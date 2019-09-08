package com.jilla.servicesexample;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

//IPC - Inter Process Communication
//here this service is executed on a different process other than the application process
//to make a service run on a different process we need to add a process tag while declaring the service in manifest file
// check the manifest file for how it is done for this service
public class MyIPCBoundService extends Service {

    private static final String TAG = MyIPCBoundService.class.getSimpleName();

    private class IncomingHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {//while debugging and checking in log cat, make sure you select the
            //current process to view messages.
            Log.e(TAG,msg.what+" in handleMessage");
            try {
                msg.replyTo.send(new Message());
                Bundle bundle  = msg.getData();
                String receivedData = bundle.getString("Welcome");
                Log.e(TAG,"received msg is "+receivedData);
            }
            catch (Exception e){
                Log.e(TAG,e.getLocalizedMessage());
            }
        }
    }

    Messenger messenger = new Messenger(new IncomingHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
