// RegisterRequest.java
package com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("email")
    private String email;
    
    @SerializedName("password")
    private String password;
    
    @SerializedName("name")
    private String name;
    
    @SerializedName("phone")
    private String phone;

    public RegisterRequest(String email, String password, String name, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
