package com.jilla.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class OrderedReceiver2 extends BroadcastReceiver {
    private static final String TAG = OrderedReceiver2.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG,"in on Receive");
        String result = getResultExtras(true).getString("Message");
        Bundle bundle = new Bundle();
        bundle.putString("Message",result+ "\nfrom OrderedReceiver2");
        setResultExtras(bundle);
    }
}
