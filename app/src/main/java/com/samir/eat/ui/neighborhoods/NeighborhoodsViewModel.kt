package com.samir.eat.ui.neighborhoods

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.samir.eat.base.BaseViewModel
import com.samir.eat.model.CommonRestaurantProperties
import com.samir.eat.repository.RestaurantsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NeighborhoodsViewModel @Inject constructor(
    private val repository: RestaurantsRepository
) : BaseViewModel() {

    private val _neighborhoods = MutableLiveData<ArrayList<CommonRestaurantProperties>>()
    val neighborhoods: LiveData<ArrayList<CommonRestaurantProperties>> = _neighborhoods

    init {
        launch {
            val response = repository.getNeighborhoods()
            _neighborhoods.value = response.neighborhoods
            currentPage = response.meta.currentPage!!
            totalPages = response.meta.totalPages!!
        }
    }

    fun loadPaginatedNeighborhoods() {
        if (currentPage == totalPages) {
            return
        }
        launch {
            val response = repository.getNeighborhoods(page = currentPage + 1)
            currentPage = response.meta.currentPage!!
            totalPages = response.meta.totalPages!!
            _neighborhoods.value = response.neighborhoods
        }
    }
}

