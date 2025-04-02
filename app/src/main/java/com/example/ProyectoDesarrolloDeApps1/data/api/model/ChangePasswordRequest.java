package com.example.ProyectoDesarrolloDeApps1.data.api.model;

public class ChangePasswordRequest {
    private String email;
    // Si tu backend requiere la nueva contraseña u otros campos, añádelos aquí:
    // private String newPassword;

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
