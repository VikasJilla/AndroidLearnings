package com.jilla.fragmentpractice;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

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
    }
}
