package com.example.ProyectoDesarrolloDeApps1.data.api;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication.ChangePasswordRequest;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication.ChangePasswordResponse;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.users.GetUserResponse;
import com.example.ProyectoDesarrolloDeApps1.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserApiService {
    @GET("getUser")
    Call<User> getUserData(@Header("Authorization") String token);

    @POST("changePassword")
    Call<ChangePasswordResponse> changePassword(@Header("Authorization") String token,
                                                @Body ChangePasswordRequest request);


}
