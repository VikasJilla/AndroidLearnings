package com.jilla.rxjavasample.network.services;

import com.jilla.rxjavasample.network.models.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface HTTPService {
    @GET("/users")
    Observable<List<User>> getUsers();
}
