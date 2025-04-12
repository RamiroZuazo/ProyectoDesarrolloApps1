package com.example.ProyectoDesarrolloDeApps1.di;

import com.example.ProyectoDesarrolloDeApps1.data.api.UserApiService;
import com.example.ProyectoDesarrolloDeApps1.data.repository.token.TokenRepository;
import com.example.ProyectoDesarrolloDeApps1.data.repository.users.UserRepository;
import com.example.ProyectoDesarrolloDeApps1.data.repository.users.UserRetrofitRepository;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class UserRepositoryModule {

    @Provides
    @Singleton
    public UserRepository provideUserRepository(UserApiService userApiService, TokenRepository tokenRepository) {
        return new UserRetrofitRepository(userApiService, tokenRepository);
    }
}
