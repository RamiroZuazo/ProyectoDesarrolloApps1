package com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication;

public class ChangePasswordRequest {
    private String email;

    public ChangePasswordRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
