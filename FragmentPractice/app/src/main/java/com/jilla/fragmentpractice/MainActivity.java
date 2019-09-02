package com.jilla.fragmentpractice;

import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final String KEY_BTN = "BTN_TXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SampleFragment fragment = new SampleFragment();
        FragmentManager manager = getSupportFragmentManager();//using support fragment manager as we used Support Fragment class
        //while implementing(extends Fragment) SampleFragment class
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.containerToAddFragmentProgramaticaly,fragment,"optionalTag");
        transaction.commit();
        transaction.addToBackStack("add Transaction");
    }

    public void replaceFragment(View view) {
//        SampleFragment fragment = new SampleFragment();
        FragmentManager manager = getSupportFragmentManager();//using support fragment manager as we used Support Fragment class
        if(view.getTag() == null) {
            //while implementing(extends Fragment) SampleFragment class
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.detach(manager.findFragmentByTag("optionalTag"));
            transaction.commit();
            view.setTag("hehe");
            Button btn = (Button) view;
            btn.setText("hellooooo");
            transaction.addToBackStack("detach Transaction");
        }
        else{
//            tends Fragment) SampleFragment class
            Fragment fragment = manager.findFragmentByTag("optionalTag");
            Bundle arguments = new Bundle();
            arguments.putString("BUNDLE_ARG","hello");
            fragment.setArguments(arguments);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.attach(manager.findFragmentByTag("optionalTag"));
            transaction.commit();
            transaction.addToBackStack("attach Transaction");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_BTN,"hellooooo");
        super.onSaveInstanceState(outState);
        Log.e(TAG,"onSaveInstanceState");
    }

    @Override
    public void onBackPressed(){
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            String transactionName = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount()-1).getName();
            ((Button)findViewById(R.id.button)).setText(transactionName);
            getSupportFragmentManager().popBackStack();
        }else super.onBackPressed();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG,"onRestoreInstanceState");
        String val = (String) savedInstanceState.getString(KEY_BTN);
        Button btn = findViewById(R.id.button);
        btn.setText(val);

    }
}
