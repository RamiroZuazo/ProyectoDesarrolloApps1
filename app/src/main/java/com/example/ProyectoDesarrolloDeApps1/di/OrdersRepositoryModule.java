package com.example.ProyectoDesarrolloDeApps1.di;

import com.example.ProyectoDesarrolloDeApps1.data.repository.orders.OrdersRetrofitRepository;
import com.example.ProyectoDesarrolloDeApps1.data.repository.orders.OrdersRepository;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
@Module
@InstallIn(SingletonComponent.class)
public class OrdersRepositoryModule {

    @Provides
    public OrdersRepository provideOrdersRepository(OrdersRetrofitRepository repository) {
        return repository;
    }
}
