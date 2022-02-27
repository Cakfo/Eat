package com.samir.eat.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samir.eat.util.SingleLiveEvent
import com.samir.eat.util.asMutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

abstract class BaseViewModel : ViewModel() {

    val error = SingleLiveEvent<String>()
    val isLoading = false.asMutableLiveData()

    protected var currentPage = 1
    protected var totalPages = 1

    protected fun launch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                block()
            } catch (e: Exception) {
                error.value = "Something went wrong"
            } finally {
                isLoading.value = false
            }
        }
    }

}