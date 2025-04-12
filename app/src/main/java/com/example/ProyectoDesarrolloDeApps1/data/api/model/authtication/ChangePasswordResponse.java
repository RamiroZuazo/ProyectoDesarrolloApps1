package com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication;

public class ChangePasswordResponse {
    private String message;

    public ChangePasswordResponse() {
    }

    public ChangePasswordResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
