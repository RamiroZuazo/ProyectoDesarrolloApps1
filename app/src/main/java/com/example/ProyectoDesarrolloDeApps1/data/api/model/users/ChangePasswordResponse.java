package com.example.ProyectoDesarrolloDeApps1.data.api.model.users;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordResponse {

    @SerializedName("message")
    private String message;

    // Getters y Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
