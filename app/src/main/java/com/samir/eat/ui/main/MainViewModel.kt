package com.samir.eat.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.samir.eat.base.BaseViewModel
import com.samir.eat.model.CommonRestaurantProperties
import com.samir.eat.repository.RestaurantsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: RestaurantsRepository
) : BaseViewModel() {

    private val textInputDelay = 300L

    private var searchJob: Job? = null

    private val _restaurants = MutableLiveData<ArrayList<CommonRestaurantProperties>>()
    val restaurants: LiveData<ArrayList<CommonRestaurantProperties>> = _restaurants

    private val priceLevelFilter = MutableLiveData<Int?>()
    private val cuisineFilter = MutableLiveData<String?>()
    private val neighborhoodsFilter = MutableLiveData<String?>()

    init {
        launch {
            val regions = repository.getRegions()
            val region = regions.find { it.attributes?.name == "Dubai" }
            val restaurantsResponse = repository.getRestaurants(regionId = region?.id)
            _restaurants.value = restaurantsResponse.restaurants
            currentPage = restaurantsResponse.meta.currentPage!!
            totalPages = restaurantsResponse.meta.totalPages!!
        }
    }

    fun searchDebounced(searchText: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(textInputDelay)
            _restaurants.value =
                repository.getRestaurants(searchQuery = searchText).restaurants
        }
    }

    fun setPriceLevelFilter(priceLevel: Int?) {
        priceLevelFilter.value = priceLevel
    }

    fun setCuisineFilters(cuisineFilter: String?) {
        this.cuisineFilter.value = cuisineFilter
    }

    fun setNeighborhoodFilters(neighborhoodFilter: String?) {
        neighborhoodsFilter.value = neighborhoodFilter
    }

    fun loadPaginatedRestaurants() {
        if (currentPage == totalPages) {
            return
        }
        launch {
            val response = repository.getRestaurants(
                page = currentPage + 1,
                priceLevel = priceLevelFilter.value,
                cuisineId = cuisineFilter.value,
                neighborhoodId = neighborhoodsFilter.value
            )
            currentPage = response.meta.currentPage!!
            totalPages = response.meta.totalPages!!
            _restaurants.value = response.restaurants
        }
    }

    fun loadFilteredRestaurants() {
        launch {
            _restaurants.value =
                repository.getRestaurants(
                    priceLevel = priceLevelFilter.value,
                    cuisineId = cuisineFilter.value,
                    neighborhoodId = neighborhoodsFilter.value
                ).restaurants
        }
    }
}