package com.jilla.servicesexample;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyIntentService extends IntentService { //onHandleIntent is called on background thread automatically
    //calling startService will deliver the intent to the onStartCommand which inturn sends the received intent to
    // intent queue, which deliver one intent at a time to onHandleIntent

    public MyIntentService(){
        super("ThreadName");
    }

    //onStartCommand -> IntentQueue -> onHandleIntent
    @Override
    protected void onHandleIntent( @Nullable Intent intent) {//runs on background thread, so long running tasks can be directly
        //done here
        int cnt = 0;
        Log.e(MyIntentService.class.getSimpleName(),Thread.currentThread().getName()+" is the current thread");
        while(cnt < 10){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cnt++;
            System.out.println(cnt+ " is the current count");
        }
        Bundle resultData = new Bundle();
        resultData.putInt("result",cnt);
        ResultReceiver receiver = intent.getParcelableExtra("resultHandler");
        receiver.send(200,resultData);
        stopSelf();
    }
}
