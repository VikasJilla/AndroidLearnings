package com.jilla.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class NormalReceiver extends BroadcastReceiver {

    private static final String TAG = NormalReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(NormalReceiver.class.getSimpleName(),"running on thread named: "+Thread.currentThread().getName());
        String receivedMessage = intent.getStringExtra("Message");
        Log.e(TAG,"receivedMessage is "+receivedMessage);
        Intent in = new Intent();
        in.setAction("dynamicDeclarationOfBroadcastReceiver");
        in.putExtra("Message","HelloWorld");
        LocalBroadcastManager.getInstance(context).sendBroadcast(in);
//        se
    }
}
