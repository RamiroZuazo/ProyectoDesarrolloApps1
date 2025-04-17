package com.example.ProyectoDesarrolloDeApps1.data.api.model.orders;

import com.google.gson.annotations.SerializedName;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecordOrdersResponse {

    // Cambiamos la estructura para que coincida con la respuesta del backend
    // La respuesta es un array directo, no un objeto con una propiedad "orders"
    private List<Pedido> pedidos;

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public static class Pedido {
        private String id;
        private String cliente;
        private String repartidorUid;
        private boolean asignado;
        private String estado;
        private String destino;

        @SerializedName("estante")
        private int estante;

        @SerializedName("gondola")
        private int gondola;

        // Modificamos estos campos para que coincidan con la estructura del backend
        private Timestamp horaInicio;
        private Timestamp horaFin;

        private int codigoRecepcion;

        // --- Getters y Setters (similares a tu clase "OrdersUnasignedResponse") ---

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCliente() {
            return cliente;
        }

        public void setCliente(String cliente) {
            this.cliente = cliente;
        }

        public String getRepartidorUid() {
            return repartidorUid;
        }

        public void setRepartidorUid(String repartidorUid) {
            this.repartidorUid = repartidorUid;
        }

        public boolean isAsignado() {
            return asignado;
        }

        public void setAsignado(boolean asignado) {
            this.asignado = asignado;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }

        public String getDestino() {
            return destino;
        }

        public void setDestino(String destino) {
            this.destino = destino;
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

        // Modificamos los getters para que devuelvan los segundos como long
        public long getHoraInicio() {
            return horaInicio != null ? horaInicio.getSeconds() : 0;
        }

        public void setHoraInicio(Timestamp horaInicio) {
            this.horaInicio = horaInicio;
        }

        public long getHoraFin() {
            return horaFin != null ? horaFin.getSeconds() : 0;
        }

        public void setHoraFin(Timestamp horaFin) {
            this.horaFin = horaFin;
        }

        public int getCodigoRecepcion() {
            return codigoRecepcion;
        }

        public void setCodigoRecepcion(int codigoRecepcion) {
            this.codigoRecepcion = codigoRecepcion;
        }

        // --- Métodos de conveniencia para formatear la hora ---

        // Ejemplo: formatear como "HH:mm" (solo hora y minutos)
        public String getHoraInicioFormatted() {
            return formatTimestamp(getHoraInicio() * 1000, "HH:mm");
        }

        public String getHoraFinFormatted() {
            return formatTimestamp(getHoraFin() * 1000, "HH:mm");
        }

        // Si prefieres un formato de fecha completo, cambia el pattern,
        // p. ej. "dd/MM/yyyy HH:mm"
        private String formatTimestamp(long timestamp, String pattern) {
            if (timestamp <= 0) {
                return "";
            }
            Date date = new Date(timestamp);
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
            return sdf.format(date);
        }
    }

    // Clase para representar el timestamp de Firestore
    public static class Timestamp {
        @SerializedName("_seconds")
        private long seconds;

        @SerializedName("_nanoseconds")
        private long nanoseconds;

        public long getSeconds() {
            return seconds;
        }

        public void setSeconds(long seconds) {
            this.seconds = seconds;
        }

        public long getNanoseconds() {
            return nanoseconds;
        }

        public void setNanoseconds(long nanoseconds) {
            this.nanoseconds = nanoseconds;
        }
    }
}
