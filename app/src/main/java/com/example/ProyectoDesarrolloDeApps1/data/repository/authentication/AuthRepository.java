package com.example.ProyectoDesarrolloDeApps1.data.repository.authentication;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication.ChangePasswordRequest;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication.RegisterRequest;

public interface AuthRepository {
    void registerUser(RegisterRequest request, AuthServiceCallback callback);
    void changePassword(ChangePasswordRequest request, ChangePasswordCallback callback);
}
