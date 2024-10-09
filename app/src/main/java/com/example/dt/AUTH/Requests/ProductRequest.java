package com.example.dt.AUTH.Requests;

import android.util.Log;

import com.example.dt.AUTH.Models.ProductListsResponse;
import com.example.dt.AUTH.Network.SocketManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.concurrent.Callable;

public class ProductRequest implements Callable<ProductListsResponse> {

    private Gson gson;

    public ProductRequest(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();

        gson = gsonBuilder.create();
    }

    @Override
    public ProductListsResponse call(){

        ProductListsResponse productListsResponse = null;

        // 1. Connect to the server
        SocketManager socketManager = null;
        if(socketManager == null){
            socketManager = SocketManager.getSocketInstance();
        }
        // 2. Create input and output streams
        BufferedReader reader       = socketManager.getReader();
        PrintWriter writer          = socketManager.getWriter();

        // 3. Send an initial request if needed
        String getProductsString = getProducts();
        System.out.println("Product Request: \n" + getProductsString);
        writer.print(getProductsString);
        writer.flush();  // Ensure the request is flushed properly

        productListsResponse = categoryResponse(reader);


        return productListsResponse ;
    }




    public String getProducts(){
        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append("GET /ProductCategories HTTP/1.1\r\n");
        requestBuilder.append("Host: 172.20.10.2\r\n");
        requestBuilder.append("Connection: KeepAlive\r\n");  // Close connection after the request
        requestBuilder.append("\r\n");  // End of headers
        return requestBuilder.toString();
    }





    public ProductListsResponse categoryResponse(BufferedReader reader){

        ProductListsResponse productListsResponse = null;

        String line = "";
        StringBuilder headerBuilder = new StringBuilder();

        try {
            // 4. Read the server's response headers
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                headerBuilder.append(line).append("\n");
            }

            // Print headers
            Log.d("Headers", headerBuilder.toString());

        String bodyline = "";
        StringBuilder dataBuilder = new StringBuilder();


            // CHUNKING =========================
            while((bodyline = reader.readLine()) != null){

                         int chunkSize = Integer.parseInt(bodyline.trim(), 16);

                         if(chunkSize == 0){
                            break;
                         }
                try{

                    char[] chunkData = new char[chunkSize];
                    reader.read(chunkData, 0, chunkSize);
                    dataBuilder.append(chunkData);

                    // Read the CRLF after the chunk data
                    reader.readLine(); // Discard the CRLF line

                }catch(NumberFormatException e){e.printStackTrace();}


            }

             productListsResponse = gson.fromJson(dataBuilder.toString(), ProductListsResponse.class);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return productListsResponse;
    }



}
