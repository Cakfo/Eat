package com.samir.eat.networking.data

import com.samir.eat.repository.RestaurantsRepository
import com.samir.eat.repository.RestaurantsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RestaurantsModule {

    companion object {
        @Singleton
        @Provides
        fun provideRestaurantsApi(serviceGenerator: ServiceGenerator): RestaurantsApi =
            serviceGenerator.createService(RestaurantsApi::class.java)
    }

    @Binds
    @Singleton
    abstract fun provideRestaurantRepository(restaurantsRepositoryImpl: RestaurantsRepositoryImpl): RestaurantsRepository
}