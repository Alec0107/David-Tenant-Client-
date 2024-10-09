package com.example.dt.AUTH.Requests;

import com.example.dt.AUTH.Network.SocketManager;
import com.example.dt.AUTH.Models.Account;
import com.example.dt.AUTH.Models.AuthResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.concurrent.Callable;

public class SignupRequest implements Callable<AuthResponse> {

    private Account account;
    private Gson gson;



    public SignupRequest(Account account){
        this.account = account;

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
    }

    @Override
    public AuthResponse call() {

        AuthResponse signupResponse = null;

        SocketManager socketManager = SocketManager.getSocketInstance();
        BufferedReader reader = socketManager.getReader();
        PrintWriter writer = socketManager.getWriter();

        // String to send to the Server
        String sendRequest = signupString();
        System.out.println("Sending Request:\n" + sendRequest + "\n"); // Debug log            writer.print();
        writer.print(sendRequest);
        writer.flush();

         signupResponse =  serverResponse(reader);
        // System.out.println("CALL METHOD: \n" + signupResponse.toString());

        socketManager.closeConnection(); // To close the connection
        return  signupResponse;
    }


    public String signupString(){

        StringBuilder contentBuilder = new StringBuilder();
        String jsonString = gson.toJson(account);
        int content_length = jsonString.getBytes().length;

        contentBuilder.append("POST /Signup HTTP/1.1\r\n");
        contentBuilder.append("Host: 172.20.10.2\r\n");
        contentBuilder.append("Connection: KeepAlive\r\n");  // Close connection after the request
        contentBuilder.append("Content-Length:").append(content_length).append("\r\n");
        contentBuilder.append("\r\n");
        contentBuilder.append(jsonString);

        return contentBuilder.toString();
    }

    public AuthResponse serverResponse(BufferedReader reader){

        AuthResponse signupResponse = null;
        String line = "";
        int contentlength = 0;
        StringBuilder responseBuilder = new StringBuilder();


        try {

               while ((line = reader.readLine()) != null && !line.isEmpty()) {

                   responseBuilder.append(line).append("\n");

                   if(line.startsWith("Content-Length:")){
                       String[] parts = line.split(":");
                       contentlength = Integer.parseInt(parts[1]);
                   }

               }

               System.out.println("Signup Request:\n" + responseBuilder.toString());

               if(contentlength > 0){
                   char[] bodyChars = new char[contentlength];
                   reader.read(bodyChars, 0, contentlength);
                   String body = new String(bodyChars);
                   signupResponse = gson.fromJson(body, AuthResponse.class);
               }




        }catch(Exception e){e.printStackTrace();}

        return signupResponse;
    }






}
