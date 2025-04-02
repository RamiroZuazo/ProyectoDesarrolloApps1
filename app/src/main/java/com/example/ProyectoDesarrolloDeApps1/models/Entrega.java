package com.example.ProyectoDesarrolloDeApps1.models;

public class Entrega {
    private String direccion;
    private String fecha;
    private String estado;

    public Entrega(String direccion, String fecha, String estado) {
        this.direccion = direccion;
        this.fecha = fecha;
        this.estado = estado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
} 