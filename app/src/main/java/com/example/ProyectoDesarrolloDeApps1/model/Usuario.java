package com.example.ProyectoDesarrolloDeApps1.model;

import java.time.LocalDateTime;

public class Usuario {
    private String uid;
    private String email;
    private String nombre;
    private String telefono;
    private LocalDateTime fechaCreacion;

    // Constructor vacío (necesario si usas serialización/deserialización)
    public Usuario() {
    }

    // Constructor con todos los campos
    public Usuario(String uid, String email, String nombre, String telefono,
                   LocalDateTime fechaCreacion) {
        this.uid = uid;
        this.email = email;
        this.nombre = nombre;
        this.telefono = telefono;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y Setters
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
