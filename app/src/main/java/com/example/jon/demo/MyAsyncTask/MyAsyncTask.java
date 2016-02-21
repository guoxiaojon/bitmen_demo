package com.example.jon.demo.MyAsyncTask;

/**
 * Created by jon on 2016/2/17.
 */
public abstract class MyAsyncTask {
    public void execute(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                doInBackground();
            }
        }).start();
    }

    public abstract void doInBackground();
}
