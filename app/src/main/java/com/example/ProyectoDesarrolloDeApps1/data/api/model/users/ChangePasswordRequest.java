package com.example.ProyectoDesarrolloDeApps1.data.api.model.users;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordRequest {

    @SerializedName("current_password")
    private String currentPassword;

    @SerializedName("new_password")
    private String newPassword;

    // Constructor
    public ChangePasswordRequest(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    // Getters y Setters
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
