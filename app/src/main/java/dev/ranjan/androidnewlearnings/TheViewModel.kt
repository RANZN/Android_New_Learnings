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

/*    private val _successResponse = MutableLiveData<List<ResponseItem>?>()
    val successResponse
        get() = _successResponse.asLiveData()

    private val _errorResponse = MutableLiveData<String?>()
    val errorResponse
        get() = _errorResponse.asLiveData()

    private val _loading = MutableLiveData(false)
    val loading
        get() = _loading.asLiveData()


    private val _response = MutableLiveData<Resource<List<ResponseItem>>>()
    val response
        get() = _response.asLiveData()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            repository.doApiCall().collect {
                _loading.postValue(false)
                when (it) {
                    is Resource.Success -> {
                        _successResponse.postValue(it.data)
                    }
                    is Resource.Error -> {
                        _errorResponse.postValue(it.message)
                    }
                    is Resource.Loading -> {
                        _loading.postValue(true)
                    }
                }
            }
        }
    }*/

    suspend fun doApiCall() = repository.doApiCall().asLiveData()


}