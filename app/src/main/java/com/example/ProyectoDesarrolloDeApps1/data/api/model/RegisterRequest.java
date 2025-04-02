// RegisterRequest.java
package com.example.ProyectoDesarrolloDeApps1.data.api.model;

public class RegisterRequest {
    private String email;
    private String password;
    private String name;
    private String phone;

    public RegisterRequest(String email, String password, String name, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }

    // getters y setters si los necesitas
}
