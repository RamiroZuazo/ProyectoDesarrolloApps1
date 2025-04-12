package com.example.ProyectoDesarrolloDeApps1.data.api.model.users;

import com.example.ProyectoDesarrolloDeApps1.model.User;
import com.google.gson.annotations.SerializedName;

public class GetUserResponse {
    @SerializedName("user")
    private User user;

    // Add a default constructor (required by Gson)
    public GetUserResponse() {
    }

    // Getter y Setter
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "GetUserResponse{" +
                "user=" + (user != null ? user.toString() : "null") +
                '}';
    }
}
