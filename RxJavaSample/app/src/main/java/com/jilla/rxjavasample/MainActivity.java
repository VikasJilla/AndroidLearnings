package com.jilla.rxjavasample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jilla.rxjavasample.datalayer.DataHolder;
import com.jilla.rxjavasample.network.RetrofitInstanceHolder;
import com.jilla.rxjavasample.network.models.User;
import com.jilla.rxjavasample.network.services.HTTPService;
import com.jilla.rxjavasample.ui.UserListAdapter;

import java.security.Permission;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements Observer, View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {
    Disposable disposable;

    @BindView(R.id.userListView)
    RecyclerView recyclerView;

    @BindView(R.id.progress_circular)
    ProgressBar progressBar;

    UserListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        listAdapter = new UserListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    protected void onResume() {
        Log.e("MainActivity","in on resume");
        super.onResume();
        checkInternetPermissionAndSendRequest();
    }

    void checkInternetPermissionAndSendRequest(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.INTERNET},100);
            }
            else {
                Toast.makeText(this,"Please give internet permission to app", Toast.LENGTH_LONG).show();
            }
        }else {
            getUsers();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                    @NonNull String[] permissions,
                                    @NonNull int[] grantResults){
        if(requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            getUsers();
        }
        else {
            Toast.makeText(this,"Not Granted",Toast.LENGTH_SHORT).show();
        }
    }

    void getUsers(){
        HTTPService service = RetrofitInstanceHolder.getRetrofitInstance().create(HTTPService.class);
        service.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        Log.e("MainActivity","in on subscribe");
        disposable = d;
    }

    @Override
    public void onNext(Object o) {
        Log.e("MainActivity","in on next");
        if(o instanceof List) {
            List userList = (List) o;
            if(((List) o).size() > 0 &&  ((List) o).get(0) instanceof User) {
                Log.e("MainActivity", "object" + userList.size());
                progressBar.setVisibility(View.GONE);
                DataHolder.getHolder().userList = userList;
                listAdapter.users = userList;
                recyclerView.setVisibility(View.VISIBLE);
                listAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e("MainActivity","in on error"+e.fillInStackTrace());
    }

    @Override
    public void onComplete() {
        Log.e("MainActivity","in on completed");
        disposable.dispose();
    }

    @Override
    public void onClick(View v) {
        int pos = recyclerView.getChildLayoutPosition(v);

    }
}
