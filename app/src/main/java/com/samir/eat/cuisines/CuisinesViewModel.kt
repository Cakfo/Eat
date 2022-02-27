package com.samir.eat.cuisines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.samir.eat.base.BaseViewModel
import com.samir.eat.model.CommonRestaurantProperties
import com.samir.eat.networking.repository.RestaurantsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CuisinesViewModel @Inject constructor(
    private val repository: RestaurantsRepository
) : BaseViewModel() {

    private val _cuisines = MutableLiveData<ArrayList<CommonRestaurantProperties>>()
    val cuisines: LiveData<ArrayList<CommonRestaurantProperties>> = _cuisines

    init {
        launch {
            val response = repository.getCuisines()
            _cuisines.value = response.cuisines
            currentPage = response.meta.currentPage!!
            totalPages = response.meta.totalPages!!
        }
    }

    fun loadPaginatedCuisines() {
        if (currentPage == totalPages) {
            return
        }
        launch {
            val response = repository.getCuisines(page = currentPage + 1)
            currentPage = response.meta.currentPage!!
            totalPages = response.meta.totalPages!!
            _cuisines.value = response.cuisines
        }
    }
}