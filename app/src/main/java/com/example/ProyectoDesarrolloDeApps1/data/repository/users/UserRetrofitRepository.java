package com.example.ProyectoDesarrolloDeApps1.data.repository.users;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import com.example.ProyectoDesarrolloDeApps1.data.api.UserApiService;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication.ChangePasswordRequest;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication.ChangePasswordResponse;
import com.example.ProyectoDesarrolloDeApps1.data.repository.token.TokenRepository;
import com.example.ProyectoDesarrolloDeApps1.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class UserRetrofitRepository implements UserRepository {

    private final UserApiService userApiService;
    private final TokenRepository tokenRepository;

    @Inject
    public UserRetrofitRepository(
            UserApiService userApiService,
            TokenRepository tokenRepository
    ) {
        this.userApiService = userApiService;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void getUserData(UserServiceCallback<User> callback) {
        String token = tokenRepository.getToken();
        Log.d(TAG, "Token being used: " + (token != null ?
                (token.length() > 10 ? token.substring(0, 5) + "..." + token.substring(token.length() - 5) : token) : "null"));

        String authHeader = "Bearer " + token;

        userApiService.getUserData(authHeader).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d(TAG, "User API response code: " + response.code());

                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    Log.d(TAG, "User from response: " + (user != null ? user.toString() : "null"));
                    callback.onSuccess(user);
                } else {
                    // Log error response body if available
                    try {
                        String errorBody = response.errorBody() != null ?
                                response.errorBody().string() : "No error body";
                        Log.e(TAG, "Error response: " + errorBody);
                    } catch (Exception e) {
                        Log.e(TAG, "Failed to read error body", e);
                    }
                    callback.onError(new Exception("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "API call failed", t);
                callback.onError(t);
            }
        });
    }



    @Override
    public void changePassword(ChangePasswordRequest request, ChangePasswordCallBack callback) {
        // Obtención del token y cambio de contraseña
        String token = tokenRepository.getToken();
        if (token == null || token.isEmpty()) {
            callback.onError(new Exception("Token no disponible"));
            return;
        }

        String authHeader = "Bearer " + token;

        userApiService.changePassword(authHeader, request).enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error al cambiar la contraseña: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
}
