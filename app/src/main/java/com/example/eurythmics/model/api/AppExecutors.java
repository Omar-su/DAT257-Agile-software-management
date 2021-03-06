package com.example.eurythmics.model.api;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
/**
 * This class created executors which will run the api calls periodically
 * @author Omar Sulaiman
 */
public class AppExecutors {

    private static AppExecutors instance;

    public static AppExecutors getInstance(){
        if (instance == null){
            instance = new AppExecutors();
        }
        return instance;
    }


    private final ScheduledExecutorService mNetworkIo = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService networkIO(){
        return mNetworkIo;
    }
}
