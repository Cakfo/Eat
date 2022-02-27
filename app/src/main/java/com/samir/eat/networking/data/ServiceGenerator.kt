package com.samir.eat.networking.data

import retrofit2.Retrofit
import javax.inject.Inject


class ServiceGenerator @Inject constructor(
    private val retrofit: Retrofit
) {
    fun <S> createService(
        serviceClass: Class<S>
    ): S {
        return retrofit.create(serviceClass)
    }
}