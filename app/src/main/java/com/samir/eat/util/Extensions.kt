package com.samir.eat.util

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> T.asLiveData(): LiveData<T> = MutableLiveData<T>().apply { value = this@asLiveData }
fun <T> T.asMutableLiveData() = MutableLiveData<T>().apply { value = this@asMutableLiveData }
fun <T> T.asSingleLiveEvent() = SingleLiveEvent<T>().apply { value = this@asSingleLiveEvent }

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}