package com.example.ProyectoDesarrolloDeApps1.data.api.model.orders;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OrdersUnasignedResponse {
    @SerializedName("orders")
    private List<Pedido> pedidos;

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public static class Pedido {
        private String id;
        
        @SerializedName("estante")
        private int estante;
        
        @SerializedName("gondola")
        private int gondola;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEstante() {
            return String.valueOf(estante);
        }

        public void setEstante(String estante) {
            try {
                this.estante = Integer.parseInt(estante);
            } catch (NumberFormatException e) {
                this.estante = 0;
            }
        }

        public String getGondola() {
            return String.valueOf(gondola);
        }

        public void setGondola(String gondola) {
            try {
                this.gondola = Integer.parseInt(gondola);
            } catch (NumberFormatException e) {
                this.gondola = 0;
            }
        }
    }
}

