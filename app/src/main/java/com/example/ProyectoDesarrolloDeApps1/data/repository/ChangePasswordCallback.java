package com.example.ProyectoDesarrolloDeApps1.data.repository;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.ChangePasswordResponse;

public interface ChangePasswordCallback {
    void onSuccess(ChangePasswordResponse response);
    void onError(Throwable error);
}
