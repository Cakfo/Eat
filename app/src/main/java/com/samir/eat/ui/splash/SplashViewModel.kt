package com.samir.eat.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.samir.eat.base.BaseViewModel
import com.samir.eat.model.RestaurantResponse
import com.samir.eat.repository.RestaurantsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: RestaurantsRepository
) : BaseViewModel() {

    private val _restaurantsResponse = MutableLiveData<RestaurantResponse>()
    val restaurantResponse: LiveData<RestaurantResponse> = _restaurantsResponse

    init {
        viewModelScope.launch {
            try {
                val regions = repository.getRegions()
                val region = regions.find { it.attributes?.name == "Dubai" }
                val restaurantsResponse = repository.getRestaurants(regionId = region?.id)
                _restaurantsResponse.value = restaurantsResponse
            } catch (e: Exception) {
                error.value = "Something went wrong"
            }
        }
    }
}