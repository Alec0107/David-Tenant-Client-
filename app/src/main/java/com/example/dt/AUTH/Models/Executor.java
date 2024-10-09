package com.example.dt.AUTH.Models;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Executor {

    private static ExecutorService executorService;

    public static ExecutorService getExecutorService(){
        if(executorService == null){
            executorService = Executors.newFixedThreadPool(2);
        }
        return executorService;
    }

    public static void makeShutdown(){
        if(executorService != null && !executorService.isShutdown()){
            executorService.shutdown();
        }
    }

}
