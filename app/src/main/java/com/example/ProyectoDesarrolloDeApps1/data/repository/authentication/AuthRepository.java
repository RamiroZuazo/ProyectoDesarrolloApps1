package com.example.ProyectoDesarrolloDeApps1.data.repository.authentication;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.ChangePasswordRequest;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.RegisterRequest;

public interface AuthRepository {
    void registerUser(RegisterRequest request, AuthServiceCallback callback);
    void changePassword(ChangePasswordRequest request, ChangePasswordCallback callback);
}
