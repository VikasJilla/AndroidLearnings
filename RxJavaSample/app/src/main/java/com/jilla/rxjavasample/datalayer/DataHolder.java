package com.jilla.rxjavasample.datalayer;

import com.jilla.rxjavasample.network.models.User;

import java.util.List;

public class DataHolder {

    private DataHolder(){}

    private static DataHolder holder;

    public List<User> userList;


    public static DataHolder getHolder(){
        if(holder == null){
            holder = new DataHolder();
        }
        return holder;
    }
}
