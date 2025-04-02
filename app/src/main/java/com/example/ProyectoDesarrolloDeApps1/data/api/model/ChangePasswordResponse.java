package com.example.ProyectoDesarrolloDeApps1.data.api.model;

public class ChangePasswordResponse {
    private boolean success;   // o la estructura que venga de tu backend
    private String message;    // por ejemplo, "Contraseña actualizada correctamente"

    // Getters y setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
