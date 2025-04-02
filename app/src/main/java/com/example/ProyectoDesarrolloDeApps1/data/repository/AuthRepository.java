package com.example.ProyectoDesarrolloDeApps1.data.repository;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.ChangePasswordRequest;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.RegisterRequest;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.RegisterResponse;

public interface AuthRepository {
    void registerUser(RegisterRequest request, AuthServiceCallback callback);
    void changePassword(ChangePasswordRequest request, ChangePasswordCallback callback);
}
