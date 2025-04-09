package com.example.ProyectoDesarrolloDeApps1.data.repository.orders;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.orders.OrdersUnasignedResponse;

public interface OrdersServiceCallback {
    void onSuccess(OrdersUnasignedResponse response);
    void onError(Throwable error);
}
