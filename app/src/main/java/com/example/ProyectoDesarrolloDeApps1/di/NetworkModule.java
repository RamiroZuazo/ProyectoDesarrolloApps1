package com.example.ProyectoDesarrolloDeApps1.di;

import com.example.ProyectoDesarrolloDeApps1.data.api.AuthApiService;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import javax.inject.Singleton;
import javax.inject.Named;

@Module
@InstallIn(SingletonComponent.class)  // Instala el módulo en el SingletonComponent
public class NetworkModule {

    @Provides
    @Singleton
    @Named("AuthRetrofit")  // Se usa Named para que Retrofit pueda ser inyectado correctamente
    public Retrofit provideAuthRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl("http://localhost:3000/auth/")  // Ajusta la URL a la de tu servidor
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public AuthApiService provideAuthApiService(@Named("AuthRetrofit") Retrofit retrofit) {
        return retrofit.create(AuthApiService.class);
    }
}

