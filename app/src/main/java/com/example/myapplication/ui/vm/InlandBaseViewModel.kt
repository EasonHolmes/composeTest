package com.example.myapplication.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class InlandBaseViewModel<Event> : ViewModel() {
    protected val modelEvnet = SingleLiveEvents<Event>()
    val uiViewEvent = modelEvnet.asLiveData()

    override fun onCleared() {
        super.onCleared()
    }
}

fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> {
    return this
}
