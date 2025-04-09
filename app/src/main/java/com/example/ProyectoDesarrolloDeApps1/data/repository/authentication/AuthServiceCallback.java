// AuthServiceCallback.java
package com.example.ProyectoDesarrolloDeApps1.data.repository.authentication;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication.RegisterResponse;

public interface AuthServiceCallback {
    void onSuccess(RegisterResponse response);
    void onError(Throwable error);
}
