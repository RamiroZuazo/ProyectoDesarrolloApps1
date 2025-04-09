package com.example.ProyectoDesarrolloDeApps1.data.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface OrdersApiService {

    @GET("pedidosNoAsignados")
    Call<com.example.ProyectoDesarrolloDeApps1.data.api.model.orders.OrdersUnasignedResponse> obtenerPedidosNoAsignados();
}

