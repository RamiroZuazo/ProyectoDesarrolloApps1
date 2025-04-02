// AuthServiceCallback.java
package com.example.ProyectoDesarrolloDeApps1.data.repository;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.RegisterResponse;

public interface AuthServiceCallback {
    void onSuccess(RegisterResponse response); // Cambiar a RegisterResponse
    void onError(Throwable error);
}
