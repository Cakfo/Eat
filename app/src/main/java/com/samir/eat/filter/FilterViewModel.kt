package com.samir.eat.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.samir.eat.base.BaseViewModel
import com.samir.eat.model.PriceLevel
import com.samir.eat.util.asMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor() : BaseViewModel() {

    private val _priceLevel = MutableLiveData<PriceLevel>()
    val priceLevel: LiveData<PriceLevel> = _priceLevel

    private val _selectedNeighborhoodId = MutableLiveData<String>()
    val selectedNeighborhoodId: LiveData<String> = _selectedNeighborhoodId

    private val _selectedCuisineId = MutableLiveData<String>()
    val selectedCuisineId: LiveData<String> = _selectedCuisineId

    private val _selectedNeighborhoodName = MutableLiveData<String>()
    val selectedNeighborhoodName: LiveData<String> = _selectedNeighborhoodName

    private val _selectedCuisineName = MutableLiveData<String>()
    val selectedCuisineName: LiveData<String> = _selectedCuisineName

    fun setPriceLevel(priceLevel: PriceLevel) {
        _priceLevel.value = priceLevel
    }

    fun setNeighborhoodId(id: String) {
        _selectedNeighborhoodId.value = id
    }

    fun setCuisineId(id: String) {
        _selectedCuisineId.value = id
    }

    fun setNeighborhoodName(name: String) {
        _selectedNeighborhoodName.value = name
    }

    fun setCuisineName(name: String) {
        _selectedCuisineName.value = name
    }
}