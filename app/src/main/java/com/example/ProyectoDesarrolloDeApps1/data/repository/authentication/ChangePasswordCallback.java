package com.example.ProyectoDesarrolloDeApps1.data.repository.authentication;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication.ChangePasswordResponse;

public interface ChangePasswordCallback {
    void onSuccess(ChangePasswordResponse response);
    void onError(Throwable error);
}
