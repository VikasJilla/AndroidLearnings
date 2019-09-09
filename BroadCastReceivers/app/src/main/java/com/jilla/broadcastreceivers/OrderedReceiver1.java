package com.jilla.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class OrderedReceiver1 extends BroadcastReceiver {
    private static final String TAG = OrderedReceiver1.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG,"in on Receive");
        Bundle bundle = getResultExtras(true);
        String result = bundle.getString("Message");
        bundle.putString("Message",result+ "\nfrom OrderedReceiver1");
        setResultExtras(bundle);
//        abortBroadcast(); --- this will result in not broadcasting the message to OrderedReceiver2 thought it has same action

    }
}
