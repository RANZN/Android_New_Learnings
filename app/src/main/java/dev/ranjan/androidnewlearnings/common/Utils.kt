package dev.ranjan.androidnewlearnings.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> {
    return this
}

fun <T> Flow<T>.asLiveData(): LiveData<T> {
    val liveData = MutableLiveData<T>()
    CoroutineScope(Dispatchers.IO).launch {
        this@asLiveData.collect {
            liveData.postValue(it)
        }
    }
    return liveData.asLiveData()
}