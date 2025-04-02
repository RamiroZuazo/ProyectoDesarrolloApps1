package com.example.ProyectoDesarrolloDeApps1.data.api;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.ChangePasswordRequest;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.ChangePasswordResponse;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.RegisterRequest;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {

    @POST("register")
    Call<RegisterResponse> register(@Body RegisterRequest request);

    @POST("changePassword")
    Call<ChangePasswordResponse> changePassword(@Body ChangePasswordRequest request);

}
