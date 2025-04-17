package com.example.ProyectoDesarrolloDeApps1.data.api;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.orders.OrdersUnasignedResponse;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.orders.RecordOrdersResponse;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface OrdersApiService {

    @GET("getOrders")
    Call<OrdersUnasignedResponse> obtenerPedidosNoAsignados(
            @Header("Authorization") String authHeader


    );

    @GET("getOrdersRecord")
    Call<List<RecordOrdersResponse.Pedido>> obtenerHistorialPedidos(
            @Header("Authorization") String authHeader
    );
}

