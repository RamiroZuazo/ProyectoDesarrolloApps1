package com.example.ProyectoDesarrolloDeApps1.data.repository.orders;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.orders.RecordOrdersResponse;

public interface OrdersRecordCallback {
    void onSuccess(RecordOrdersResponse response);
    void onError(Throwable error);
}
