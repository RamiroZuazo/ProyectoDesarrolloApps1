package com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication;

public class ChangePasswordRequest {
    private String email;
    private String currentPassword;
    private String newPassword;

    // Constructor para recuperar contraseña (solo email)
    public ChangePasswordRequest(String email) {
        this.email = email;
        this.currentPassword = null;
        this.newPassword = null;
    }

    // Constructor para cambiar contraseña (contraseña actual y nueva)
    public ChangePasswordRequest(String currentPassword, String newPassword) {
        this.email = null;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
