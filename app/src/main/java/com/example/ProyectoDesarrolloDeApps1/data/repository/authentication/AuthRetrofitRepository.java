package com.example.ProyectoDesarrolloDeApps1.data.repository.authentication;

import com.example.ProyectoDesarrolloDeApps1.data.api.AuthApiService;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication.ChangePasswordRequest;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication.ChangePasswordResponse;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication.RegisterRequest;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication.RegisterResponse;
import com.example.ProyectoDesarrolloDeApps1.model.User;  // Aquí es 'User' no 'Usuario'

import org.json.JSONObject;

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
                    RegisterResponse res = response.body();
                    RegisterResponse.AuthUser authUser = res.getUser();

                    // Usamos la clase 'User' en lugar de 'Usuario'
                    User user = new User(
                            authUser.getUid(),
                            authUser.getEmail(),
                            authUser.getName(),
                            authUser.getPhone()
                    );

                    callback.onSuccess(res);
                } else {
                    // Aquí extraemos el mensaje de error si existe
                    if (response.errorBody() != null) {
                        try {
                            // Parseamos el cuerpo de la respuesta de error para obtener el mensaje
                            String errorResponse = response.errorBody().string();
                            // Este es el mensaje que contiene el error del servidor
                            String errorMessage = new JSONObject(errorResponse).getString("error");

                            // Llamamos a onError con el mensaje de error
                            callback.onError(new Exception(errorMessage));
                        } catch (Exception e) {
                            callback.onError(new Exception("Error desconocido: " + e.getMessage()));
                        }
                    }
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
