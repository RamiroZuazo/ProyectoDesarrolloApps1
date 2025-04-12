package com.example.ProyectoDesarrolloDeApps1.data.repository.users;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication.ChangePasswordResponse;

public interface ChangePasswordCallBack {
    void onSuccess(ChangePasswordResponse response);
    void onError(Throwable error);
}
