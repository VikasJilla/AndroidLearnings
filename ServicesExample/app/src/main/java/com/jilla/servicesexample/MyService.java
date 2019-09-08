package com.jilla.servicesexample;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.logging.Logger;

// a service that is created by extending Service class runs every method on main thread - indicating that no long running
//tasks should be done in the functions
public class MyService extends Service {

    @Override
    public void onCreate() {//will be called only once
        super.onCreate();
        Log.e(MyService.class.getSimpleName(),"onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {//will be called as many times startService is called,
        //and the intent passed to start service will be received here
        Log.e(MyService.class.getSimpleName(),"onStartCommand is running on " + Thread.currentThread().getName());
        new BackgroundTask().execute();
        return START_NOT_STICKY;//if this process is killed(by OS due to lack of system resources)
        // after onStartCommand is started, then this flag will tell not to pass the
        //intent when the service is recreated.
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(MyService.class.getSimpleName(),"onBind");
        return null;//returning null indicates "started service" - i.e, this service is not bound to any
        // Activity or Android Component
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(MyService.class.getSimpleName(),"onDestroy");
    }

    class BackgroundTask extends AsyncTask<Void,Integer,Void>{

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
                    Thread.sleep(1000);
                    Log.e("MyService",""+cnt);
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
            Toast.makeText(getApplicationContext(),"Count updated to "+values[0],Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.e("MyService","onPostExecute");
            Intent intent = new Intent();
            intent.setAction("startedServiceResult");
            intent.putExtra("result",200);
            sendBroadcast(intent);
            stopSelf();//stops the service that has started this thread
        }
    }
}
