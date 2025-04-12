package com.example.ProyectoDesarrolloDeApps1.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("uid")
    private String uid;

    @SerializedName("email")
    private String email;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @SerializedName("name")
    private String name;

    @SerializedName("phone")
    private String phone;

    // Default constructor for Gson
    public User() {
    }

    // Constructor
    public User(String uid, String email, String name, String phone) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    // Getters y Setters remain the same

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}