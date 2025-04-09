package com.example.ProyectoDesarrolloDeApps1.data.repository.orders;

import com.example.ProyectoDesarrolloDeApps1.data.api.OrdersApiService;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.orders.OrdersUnasignedResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class OrdersRetrofitRepository implements OrdersRepository {

    private final OrdersApiService ordersApiService;

    @Inject
    public OrdersRetrofitRepository(OrdersApiService ordersApiService) {
        this.ordersApiService = ordersApiService;
    }

    @Override
    public void obtenerPedidosNoAsignados(OrdersServiceCallback callback) {
        ordersApiService.obtenerPedidosNoAsignados().enqueue(new Callback<OrdersUnasignedResponse>() {
            @Override
            public void onResponse(Call<OrdersUnasignedResponse> call, Response<OrdersUnasignedResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    OrdersUnasignedResponse pedidoResponse = response.body();
                    // Llamamos a callback.onSuccess con la respuesta de los pedidos
                    callback.onSuccess(pedidoResponse);
                } else {
                    callback.onError(new Exception("Error en la respuesta: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<OrdersUnasignedResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
}
