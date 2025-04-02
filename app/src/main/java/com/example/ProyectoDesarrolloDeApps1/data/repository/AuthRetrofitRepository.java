package com.example.ProyectoDesarrolloDeApps1.data.repository;

import com.example.ProyectoDesarrolloDeApps1.data.api.AuthApiService;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.ChangePasswordRequest;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.ChangePasswordResponse;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.RegisterRequest;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.RegisterResponse;
import com.example.ProyectoDesarrolloDeApps1.model.Usuario;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class AuthRetrofitRepository implements AuthRepository {

    private final AuthApiService authApiService;

    @Inject
    public AuthRetrofitRepository(AuthApiService authApiService) {
        this.authApiService = authApiService;
    }

    @Override
    public void registerUser(RegisterRequest request, AuthServiceCallback callback) {
        authApiService.register(request).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Mapeamos la respuesta de la API a Usuario
                    RegisterResponse res = response.body();
                    RegisterResponse.AuthUser authUser = res.getUser();

                    Usuario usuario = new Usuario(
                            authUser.getUid(),
                            authUser.getEmail(),
                            authUser.getName(),
                            authUser.getPhone(),
                            null // Puede recibir fecha de creación si tu backend la devuelve
                    );

                    // Llamamos a callback.onSuccess con el usuario
                    callback.onSuccess(res);  // Pasamos RegisterResponse a onSuccess
                } else {
                    callback.onError(new Exception("Error en la respuesta: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
    // Implementación del cambio de contraseña
    @Override
    public void changePassword(ChangePasswordRequest request, ChangePasswordCallback callback) {
        authApiService.changePassword(request).enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Aquí puedes manejar la respuesta de la forma que necesites
                    ChangePasswordResponse changePasswordResponse = response.body();
                    callback.onSuccess(changePasswordResponse);
                } else {
                    callback.onError(new Exception("Error en la respuesta: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

}
