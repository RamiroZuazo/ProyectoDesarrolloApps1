package com.example.ProyectoDesarrolloDeApps1.data.api.model.orders;

import java.util.List;

public class OrdersUnasignedResponse {
    private List<Pedido> pedidos;

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public static class Pedido {
        private String id;
        private String estante;
        private String gondola;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEstante() {
            return estante;
        }

        public void setEstante(String estante) {
            this.estante = estante;
        }

        public String getGondola() {
            return gondola;
        }

        public void setGondola(String gondola) {
            this.gondola = gondola;
        }
    }
}

