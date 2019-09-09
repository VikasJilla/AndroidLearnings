package com.jilla.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    /*
        4 types of broadcast receivers
        1. Normal
        2. Ordered Broadcast Receivers -- when there are multiple receivers with same action in intent filter we give priority
        to the receivers and the data is sent from one receiver to another receiver, if the first receiver calls abort() then the
        broadcast will be stopped and won't be forwarder. or if the first broad cast changes the intent it received and set it as
        the resultData then this modified data will be received by the next one. Same things can happen in the following braodcast
        receivers.
        3. Sticky - while registering for sticky boradcasts we need to send null for registerReceiver(null,intentFilter);
            the return value of the register receiver will be an intent that will hold the last updated value for the action
            specified. used only in few cases
        4. Local Broadcast receiver

        NOTE: To send broadcast to other apps we use permission and uses permission tags in manifest file
        permission tag is specified in the app that sends broadcast and uses-permission tag should be specified in the app
        that receives broadcast.
    */

    BroadcastReceiver myBroadCastReceiver = new BroadcastReceiver() {//local broadcast receiver
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG,"onReceive is running on "+Thread.currentThread().getName()+" thread "+intent.getStringExtra("Message"));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //dynamic broadcast receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction("dynamicDeclarationOfBroadcastReceiver");//action name is not meaningful but bare with it, this is an
        //example for both dynamic broadcast receiver and local broadcast receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(myBroadCastReceiver,filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myBroadCastReceiver);
    }

    public void sendNormalBroadcast(View view) {
        Intent in = new Intent(this,NormalReceiver.class);
        in.putExtra("Message","from sendNormalBroadcast");
        sendBroadcast(in);
    }

    public void sendActionBroadcast(View view) {
        Intent in = new Intent();
        in.setAction("start_action_normal_receiver");
        in.putExtra("Message","from sendActionBroadcast");
        sendBroadcast(in);
    }

    public void sendDynamicBroadcast(View v){
        Intent in = new Intent();
        in.setAction("dynamicDeclarationOfBroadcastReceiver");
        //sendBroadcast(in);-->we cannot use this as the broadcast receiver is registered with local broadcast manager
        //so we use as below
        LocalBroadcastManager.getInstance(this).sendBroadcast(in);

    }

    public void sendOrderedBroadcast(View view) {
        Intent intent = new Intent();
        intent.setAction("orderedAction");
        Bundle bundle = new Bundle();
        bundle.putString("Message","\nfrom MainActivity");
        sendOrderedBroadcast(intent, null, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e(TAG,"inOrderedBroadcast on receive running on "+Thread.currentThread().getName()+" thread");
                String message = getResultExtras(true).getString("Message");
                Log.e(TAG,"in OrdereBroadcast on receiver result message is "+message);
            }
        },null,4,"",bundle);
    }
}
