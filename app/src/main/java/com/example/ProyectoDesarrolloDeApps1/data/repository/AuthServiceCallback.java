// AuthServiceCallback.java
package com.example.ProyectoDesarrolloDeApps1.data.repository;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.RegisterResponse;

public interface AuthServiceCallback {
    void onSuccess(RegisterResponse response);
    void onError(Throwable error);
}
