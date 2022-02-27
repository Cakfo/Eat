package com.samir.eat.ui.cuisines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.samir.eat.base.BaseViewModel
import com.samir.eat.model.CommonRestaurantProperties
import com.samir.eat.networking.data.ResourceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CuisinesViewModel @Inject constructor() : BaseViewModel() {

    private val _cuisines = MutableLiveData<ArrayList<CommonRestaurantProperties>>()
    val cuisines: LiveData<ArrayList<CommonRestaurantProperties>> = _cuisines

    init {
        launch {
            _cuisines.value = ResourceManager.cuisines
        }
    }
}