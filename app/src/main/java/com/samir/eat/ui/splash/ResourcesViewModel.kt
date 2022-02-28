package com.samir.eat.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.samir.eat.base.BaseViewModel
import com.samir.eat.model.CommonRestaurantProperties
import com.samir.eat.model.RestaurantResponse
import com.samir.eat.repository.RestaurantsRepository
import com.samir.eat.networking.data.ResourceManager
import com.samir.eat.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ResourcesViewModel @Inject constructor(
    private val repository: RestaurantsRepository
) : BaseViewModel() {

    val finishedLoading = SingleLiveEvent<Unit>()
    private val _restaurantResponse = MutableLiveData<RestaurantResponse>()
    val restaurantResponse: LiveData<RestaurantResponse> = _restaurantResponse

    init {
        viewModelScope.launch {
            supervisorScope {
                try {
                    awaitAll(async {
                        val regions = repository.getRegions()
                        ResourceManager.regions.addAll(regions)
                        val region = regions.find { it.attributes?.name == "Dubai" }
                        _restaurantResponse.value = repository.getRestaurants(regionId = region?.id)
                    }, async {
                        ResourceManager.neighborhoods.addAll(repository.getNeighborhoods(limit = 1000).neighborhoods)
                    }, async {
                        ResourceManager.cuisines.addAll(repository.getCuisines(limit = 1000).cuisines)
                    })
                    finishedLoading.call()
                } catch (e: Exception) {
                    error.value = "Something went wrong"
                }
            }
        }
    }
}