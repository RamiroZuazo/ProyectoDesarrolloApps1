package com.example.ProyectoDesarrolloDeApps1.di;

import com.example.ProyectoDesarrolloDeApps1.data.repository.orders.OrdersRetrofitRepository;
import com.example.ProyectoDesarrolloDeApps1.data.repository.orders.OrdersRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class OrdersRepositoryModule {

    @Provides
    public OrdersRepository provideOrdersRepository(OrdersRetrofitRepository repository) {
        return repository;
    }
}
