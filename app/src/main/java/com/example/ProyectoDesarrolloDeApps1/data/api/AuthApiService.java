package com.example.ProyectoDesarrolloDeApps1.data.api;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication.ChangePasswordRequest;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication.ChangePasswordResponse;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication.RegisterRequest;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {

    @POST("register")
    Call<RegisterResponse> register(@Body RegisterRequest request);

    @POST("changePassword")
    Call<ChangePasswordResponse> changePassword(@Body ChangePasswordRequest request);

}
