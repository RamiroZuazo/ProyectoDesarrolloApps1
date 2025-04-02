package com.example.ProyectoDesarrolloDeApps1.data.repository;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.Entrega;
import java.util.List;
import java.util.ArrayList;

import javax.inject.Inject;

public class EntregaRepository {
    public interface EntregaCallback {
        void onSuccess(List<Entrega> entregas);
        void onError(String error);
    }

    @Inject
    public EntregaRepository() {
        // Constructor vacío con @Inject para Hilt
    }

    public void getEntregasAsignadas(EntregaCallback callback) {
        // TODO: Implementar la llamada real a la API
        // Por ahora, retornamos datos de ejemplo
        List<Entrega> entregasEjemplo = new ArrayList<>();
        entregasEjemplo.add(new Entrega("Av. Corrientes 1234", "15/03/2024", "Pendiente"));
        entregasEjemplo.add(new Entrega("Av. Santa Fe 567", "14/03/2024", "En proceso"));
        entregasEjemplo.add(new Entrega("Av. Cabildo 890", "13/03/2024", "Entregado"));
        
        callback.onSuccess(entregasEjemplo);
    }

    public void getHistorialEntregas(EntregaCallback callback) {
        // TODO: Implementar la llamada real a la API
        // Por ahora, retornamos datos de ejemplo
        List<Entrega> entregasEjemplo = new ArrayList<>();
        entregasEjemplo.add(new Entrega("Av. Corrientes 1234", "15/03/2024", "Entregado"));
        entregasEjemplo.add(new Entrega("Av. Santa Fe 567", "14/03/2024", "Entregado"));
        entregasEjemplo.add(new Entrega("Av. Cabildo 890", "13/03/2024", "Entregado"));
        
        callback.onSuccess(entregasEjemplo);
    }
} 