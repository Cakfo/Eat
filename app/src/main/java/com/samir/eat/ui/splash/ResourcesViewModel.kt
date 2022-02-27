package com.samir.eat.ui.splash

import androidx.lifecycle.viewModelScope
import com.samir.eat.base.BaseViewModel
import com.samir.eat.repository.RestaurantsRepository
import com.samir.eat.networking.data.ResourceManager
import com.samir.eat.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResourcesViewModel @Inject constructor(
    private val repository: RestaurantsRepository
) : BaseViewModel() {

    val finishedLoading = SingleLiveEvent<Unit>()

    init {
        viewModelScope.launch {
            try {
                awaitAll(async {
                    ResourceManager.regions.addAll(repository.getRegions())
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