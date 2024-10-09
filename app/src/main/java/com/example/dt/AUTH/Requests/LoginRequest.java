package com.example.dt.AUTH.Requests;

import com.example.dt.AUTH.Models.Account;
import com.example.dt.AUTH.Models.AuthResponse;
import com.example.dt.AUTH.Network.SocketManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.concurrent.Callable;

public class LoginRequest implements Callable<AuthResponse>{

    private Account account;
    private Gson gson;

    public LoginRequest(Account account){
        this.account = account;

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
    }

    @Override
    public AuthResponse call() {

        AuthResponse authResponse = null;

       SocketManager socketManager = SocketManager.getSocketInstance();
       BufferedReader reader = socketManager.getReader();
       PrintWriter writer = socketManager.getWriter();

       // SENDING HTTP REQUEST FOR LOGIN
       String jsonString =  loginString();
       System.out.println(jsonString);
       writer.print(jsonString);
       writer.flush();

        // RECEIVING RESPONSE FROM SERVER
        authResponse = loginResponse(reader);
        if(authResponse.isExist()){
            socketManager.closeConnection();
        }

        System.out.println(authResponse);

        socketManager.closeConnection();


        return authResponse;
    }

    public String loginString(){

        StringBuilder contentBuilder = new StringBuilder();
        String jsonString = gson.toJson(account);
        int content_length = jsonString.getBytes().length;

        contentBuilder.append("POST /Login HTTP/1.1\r\n");
        contentBuilder.append("Host: 172.20.10.2\r\n");
        contentBuilder.append("Connection: KeepAlive\r\n");  // Close connection after the request
        contentBuilder.append("Content-Length:").append(content_length).append("\r\n");
        contentBuilder.append("\r\n");
        contentBuilder.append(jsonString);


        return contentBuilder.toString();
    }

    public AuthResponse loginResponse(BufferedReader reader){
        AuthResponse authResponse = null;

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

            System.out.println(responseBuilder);

            if(contentlength > 0){
                char[] bodyChars = new char[contentlength];
                reader.read(bodyChars, 0, contentlength);
                String content = new String(bodyChars);
                authResponse = gson.fromJson(content, AuthResponse.class);
            }

        }catch(Exception e){e.printStackTrace();}

        return authResponse;
    }
}
