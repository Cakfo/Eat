package com.samir.eat.repository

import com.samir.eat.model.CommonRestaurantProperties
import com.samir.eat.model.CuisinesResponse
import com.samir.eat.model.NeighborhoodsResponse
import com.samir.eat.model.RestaurantResponse
import com.samir.eat.networking.data.RestaurantsApi
import javax.inject.Inject

class RestaurantsRepositoryImpl @Inject constructor(
    private val restaurantsApi: RestaurantsApi
) : RestaurantsRepository {

    override suspend fun getCuisines(page: Int): CuisinesResponse {
        return restaurantsApi.getCuisines(page)
    }

    override suspend fun getNeighborhoods(page: Int): NeighborhoodsResponse {
        return restaurantsApi.getNeighborhoods(page)
    }

    override suspend fun getRegions(): ArrayList<CommonRestaurantProperties> {
        return restaurantsApi.getRegions().regions
    }

    override suspend fun getRestaurants(
        page: Int,
        cuisineId: String?,
        neighborhoodId: String?,
        regionId: String?,
        priceLevel: Int?,
        searchQuery: String?
    ): RestaurantResponse {
        return restaurantsApi.getRestaurants(
            page,
            cuisineId,
            neighborhoodId,
            regionId,
            priceLevel,
            searchQuery
        )
    }
}