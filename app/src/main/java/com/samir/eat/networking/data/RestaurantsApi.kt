package com.samir.eat.networking.data

import com.samir.eat.model.CuisinesResponse
import com.samir.eat.model.NeighborhoodsResponse
import com.samir.eat.model.RegionsResponse
import com.samir.eat.model.RestaurantResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantsApi {
    @GET("/consumer/v2/restaurants")
    suspend fun getRestaurants(
        @Query("page") page: Int = 1,
        @Query("cuisine_id") cuisineId: String? = null,
        @Query("neighborhood_id") neighborhoodId: String? = null,
        @Query("region_id") regionId: String? = null,
        @Query("price_level") priceLevel: Int? = null,
        @Query("q") searchQuery: String? = null
    ): RestaurantResponse

    @GET("/consumer/v2/cuisines")
    suspend fun getCuisines(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 30
    ): CuisinesResponse

    @GET("/consumer/v2/neighborhoods")
    suspend fun getNeighborhoods(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 30
    ): NeighborhoodsResponse

    @GET("/consumer/v2/regions")
    suspend fun getRegions(): RegionsResponse
}