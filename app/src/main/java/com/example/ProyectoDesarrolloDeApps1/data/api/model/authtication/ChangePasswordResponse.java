package com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication;

public class ChangePasswordResponse {
    private boolean success;
    private String message;

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
