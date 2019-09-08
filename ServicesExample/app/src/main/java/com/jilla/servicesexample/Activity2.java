package com.jilla.servicesexample;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Activity2 extends AppCompatActivity {

    private static final String TAG = Activity2.class.getSimpleName() ;
    MyBoundService mService;
    boolean mIsBounded;
    Messenger messenger = null;
    ServiceConnection mIPCServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG,"component name is "+name.getClassName()+"\n in onService Ipc onServiceConnected");
            messenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG,"in onServiceConnected");
            mService = ((MyBoundService.MyBinder)service).getService();
            mIsBounded = true;
            doSomethingWithBoundService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsBounded = false;
            Log.e(TAG,"onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
    }


    public void startBoundedService(View view){
        Intent intent = new Intent(this,MyBoundService.class);
        bindService(intent,mConnection,BIND_AUTO_CREATE);
    }

    public void stopBoundedService(View view){
        if(mIsBounded) {
            unbindService(mConnection);//will unbind this activity from the service
            mIsBounded = false;
        }
        //NOTE: when there are no android components binded to a service, then the service stops automatically
    }

    void doSomethingWithBoundService(){
        mService.add(10,20);
    }

    public void startIPCBoundService(View view) {
        Intent intent = new Intent(this,MyIPCBoundService.class);
        bindService(intent,mIPCServiceConnection,BIND_AUTO_CREATE);
    }

    public void stopIPCBoundService(View v){
        unbindService(mIPCServiceConnection);
    }

    public void sendMessage(View view){
        Message message = new Message();//.obtain(null,42);
        Bundle bundle = new Bundle();
        bundle.putString("Welcome","Test Value");
        message.setData(bundle);
        message.replyTo = responseMessenger;
        try {
            Log.e(TAG,"sending messge");
            messenger.send(message);
            Log.e(TAG,"message sent");
        } catch (RemoteException e) {
            Log.e(TAG,"some exception occured"+e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
    Messenger responseMessenger = new Messenger(new ResponseHandler());
    private class ResponseHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e(TAG,"in on Handle message");
        }
    }
}

