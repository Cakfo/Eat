package com.samir.eat.networking.repository

import com.samir.eat.model.CommonRestaurantProperties
import com.samir.eat.model.CuisinesResponse
import com.samir.eat.model.NeighborhoodsResponse
import com.samir.eat.model.RestaurantResponse

interface RestaurantsRepository {
    suspend fun getCuisines(page: Int = 1): CuisinesResponse
    suspend fun getNeighborhoods(page: Int = 1): NeighborhoodsResponse
    suspend fun getRegions(): ArrayList<CommonRestaurantProperties>
    suspend fun getRestaurants(
        page: Int = 1,
        cuisineId: String? = null,
        neighborhoodId: String? = null,
        regionId: String? = null,
        priceLevel: Int? = null,
        searchQuery: String? = null
    ): RestaurantResponse
}