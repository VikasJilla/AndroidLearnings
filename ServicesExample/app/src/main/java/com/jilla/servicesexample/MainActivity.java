package com.jilla.servicesexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView starteServeResult,intentServeResult;
    BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        starteServeResult = findViewById(R.id.startedServiceResult);
        intentServeResult = findViewById(R.id.intentServiceResult);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int result = intent.getIntExtra("result",-10);
                starteServeResult.setText(result+" returned result from started service");
            }
        };
    }

    public void managerService(View view) {
        if(view.getTag() == null){
            Intent intent = new Intent(this,MyService.class);
            startService(intent);//starting a service multiple times won't create it again, once it starts,
            // it can' be recreated until it is destroyed, if you call startService without destroying it
            // onStartCommand of the service will be executed each time
            view.setTag("tag");
        }
        else {
            Intent intent = new Intent(this,MyService.class);
            stopService(intent);
            view.setTag(null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("startedServiceResult");
        registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            unregisterReceiver(broadcastReceiver);
        }catch (Exception e){
            Log.e("MainActivity","Error occured");
        }
    }

    public void manageIntentService(View view) {
        if(view.getTag() == null){
            Intent intent = new Intent(this,MyIntentService.class);
            intent.putExtra("resultHandler",new MyResultHandler(handler));//instead of null if we pass handler
            // then the onReceiveResult will be called on the thread to which handler was attached
            startService(intent);
            view.setTag("IntentService");

        }
        else{
            Intent in = new Intent(this,MyIntentService.class);
            stopService(in);
            view.setTag(null);
        }
    }
    Handler handler = new Handler();

    public void launchSecondActivity(View view) {
        Intent in = new Intent(this,Activity2.class);
        startActivity(in);
    }

    private class MyResultHandler extends ResultReceiver{

        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public MyResultHandler(Handler handler) {
            super(handler);
        }

        @Override
        protected  void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            Log.e(this.getClass().getSimpleName(),Thread.currentThread().getName()+" thread onReceiveResult is runnning in");
            if(resultCode == 200){
                int result = resultData.getInt("result",-10);
                final String data = result + getApplicationContext().getString(R.string.cnt_returned);
                intentServeResult.setText(data);
            }
        }
    }

    class BTask extends AsyncTask<Void,Integer,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void[] objects) {
            int cnt = 0;
            while(cnt < 10){
                cnt++;
                try {
                    Log.e("MyS",""+cnt);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(cnt);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.e("MyServices","onProgressUpdate "+values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.e("MyServices","onPostExecuted");
        }
    }
}

