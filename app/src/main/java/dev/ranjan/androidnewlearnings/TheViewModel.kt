package dev.ranjan.androidnewlearnings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ranjan.androidnewlearnings.common.asLiveData
import dev.ranjan.androidnewlearnings.data.Repository
import javax.inject.Inject

@HiltViewModel
class TheViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    suspend fun getToken() = repository.getToken().asLiveData()

//    suspend fun getData() = repository.getData().asLiveData()

}