package com.samir.eat.ui.neighborhoods

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.samir.eat.base.BaseViewModel
import com.samir.eat.model.CommonRestaurantProperties
import com.samir.eat.networking.data.ResourceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NeighborhoodsViewModel @Inject constructor() : BaseViewModel() {

    private val _neighborhoods = MutableLiveData<ArrayList<CommonRestaurantProperties>>()
    val neighborhoods: LiveData<ArrayList<CommonRestaurantProperties>> = _neighborhoods

    init {
        launch {
            _neighborhoods.value = ResourceManager.neighborhoods
        }
    }
}

