package dev.ranjan.androidnewlearnings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ranjan.androidnewlearnings.common.Resource
import dev.ranjan.androidnewlearnings.common.asLiveData
import dev.ranjan.androidnewlearnings.data.Repository
import dev.ranjan.androidnewlearnings.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class TheViewModel @Inject constructor(
    private val repository: Repository, private val apiService: ApiService
) : ViewModel() {
    init {
        viewModelScope.launch {
            val response = apiService.check()
            Log.d("ranjan", "init: $response")
        }
    }

    private val _response = MutableLiveData<Resource<String>>()
    val response get() = _response.asLiveData()
    fun sendData(file: File) {
        viewModelScope.launch(Dispatchers.IO) {
            val result: Flow<Resource<String>> = repository.doApiCall(file)
            withContext(Dispatchers.Main) {
                result.collect {
                    _response.postValue(it)
                }
            }
        }
    }

}