package com.example.ProyectoDesarrolloDeApps1.data.api;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.RegisterRequest;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {

    @POST("register")
    Call<RegisterResponse> register(@Body RegisterRequest request);

    // Si tuvieras login, también lo defines:
    // @POST("login")
    // Call<LoginResponse> login(@Body LoginRequest request);
}
