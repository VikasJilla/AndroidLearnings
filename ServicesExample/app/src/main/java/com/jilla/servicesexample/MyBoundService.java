package com.jilla.servicesexample;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


public class MyBoundService extends Service {
    class MyBinder extends Binder {

        public MyBoundService getService(){
            return MyBoundService.this;
        }
    }

    private static final String TAG = MyBoundService.class.getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"in OnStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    public int add(int a, int b){
        Log.e(MyBoundService.class.getSimpleName(),Thread.currentThread().getName()+" is thread in which add() method is running");
        return a+b;
    }

    @Override
    public void onDestroy() {
        Log.e(TAG,"in onDestroy");
        super.onDestroy();
    }
}
