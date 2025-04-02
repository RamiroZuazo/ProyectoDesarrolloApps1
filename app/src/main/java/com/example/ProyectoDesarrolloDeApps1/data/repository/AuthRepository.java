package com.example.ProyectoDesarrolloDeApps1.data.repository;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.RegisterRequest;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.RegisterResponse;

public interface AuthRepository {
    void registerUser(RegisterRequest request, AuthServiceCallback callback);
    // Aquí podrías añadir loginUser(LoginRequest request, AuthServiceCallback callback) si lo deseas
}
