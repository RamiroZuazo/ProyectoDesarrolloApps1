package com.example.ProyectoDesarrolloDeApps1.data.api.model;

public class Entrega {
    private String id;
    private String direccion;
    private String estado;
    private String fecha;
    private String cliente;
    private String descripcion;

    public Entrega(String id, String direccion, String estado, String fecha, String cliente, String descripcion) {
        this.id = id;
        this.direccion = direccion;
        this.estado = estado;
        this.fecha = fecha;
        this.cliente = cliente;
        this.descripcion = descripcion;
    }

    // Constructor para datos de ejemplo
    public Entrega(String direccion, String fecha, String estado) {
        this.direccion = direccion;
        this.fecha = fecha;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
} 