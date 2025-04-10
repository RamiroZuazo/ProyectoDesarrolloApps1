package com.example.ProyectoDesarrolloDeApps1.data.repository.orders;

import com.example.ProyectoDesarrolloDeApps1.data.api.OrdersApiService;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.orders.OrdersUnasignedResponse;
import com.example.ProyectoDesarrolloDeApps1.data.repository.token.TokenRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class OrdersRetrofitRepository implements OrdersRepository {

    private final OrdersApiService ordersApiService;
    private final TokenRepository tokenRepository;

    @Inject
    public OrdersRetrofitRepository(
            OrdersApiService ordersApiService,
            TokenRepository tokenRepository
    ) {
        this.ordersApiService = ordersApiService;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void obtenerPedidosNoAsignados(OrdersServiceCallback callback) {
        String token = tokenRepository.getToken();
        String authHeader = "Bearer " + token;

        ordersApiService.obtenerPedidosNoAsignados(authHeader)
                .enqueue(new Callback<OrdersUnasignedResponse>() {
                    @Override
                    public void onResponse(Call<OrdersUnasignedResponse> call,
                                           Response<OrdersUnasignedResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            callback.onSuccess(response.body());
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
