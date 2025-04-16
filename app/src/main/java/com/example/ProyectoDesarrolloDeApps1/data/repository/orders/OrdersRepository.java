package com.example.ProyectoDesarrolloDeApps1.data.repository.orders;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.orders.OrdersUnasignedResponse;

public interface OrdersRepository {
    void obtenerPedidosNoAsignados(OrdersServiceCallback callback);
    void obtenerHistorialPedidos(OrdersRecordCallback callback);
}


