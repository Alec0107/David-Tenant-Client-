package com.example.dt.AUTH.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketManager {

    private static SocketManager instance;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private final String  SERVER_IP   = "192.168.0.150";
    private final int     SERVER_PORT = 8080;


    private SocketManager(){
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            System.out.println("Socket connected to server: " + SERVER_IP + ":" + SERVER_PORT);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static SocketManager getSocketInstance(){
        if(instance == null){
            instance = new SocketManager();
        }
        return instance;
    }

    public BufferedReader getReader(){
        return reader;
    }

    public PrintWriter getWriter(){
        return writer;
    }

    public void closeConnection() {
        try {
            writer.close();
            reader.close();
            socket.close();
            instance = null; // Clear instance to allow reconnection if needed
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
