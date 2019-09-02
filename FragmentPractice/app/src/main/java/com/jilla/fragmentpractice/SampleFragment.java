package com.jilla.fragmentpractice;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.zip.Inflater;

public class SampleFragment extends Fragment {

    private static final String TAG = SampleFragment.class.getSimpleName();

    TextView textView;

    @Override
    public void onAttach(Context ctx){
        super.onAttach(ctx);
        Log.e(TAG,"onAttach");
    }

    @Override
    public void onCreate(Bundle state){
        super.onCreate(state);
        Log.e(TAG,"onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View view = inflater.inflate(R.layout.sample_fragment,container,false);
        Log.e(TAG,"onCreateView");
        textView = view.findViewById(R.id.textView);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle state){
        super.onActivityCreated(state);
        Bundle bd = getArguments();
        if(state != null || bd != null) {
            bd = state != null?state:bd;
            String s = bd.getString("BUNDLE_ARG", "test");
            textView.setText(s);
        }
        Log.e(TAG,"onActivityCreated");
    }

    @Override
    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        bundle.putString("BUNDLE_ARG","test");
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.e(TAG,"onStart");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.e(TAG,"onResume");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.e(TAG,"onPause");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.e(TAG,"onStop");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.e(TAG,"onDestroy");
    }

    @Override
    public void onDetach(){
        super.onDetach();
        Log.e(TAG,"onDetach");
    }

}
