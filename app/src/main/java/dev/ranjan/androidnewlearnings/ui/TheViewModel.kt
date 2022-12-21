package dev.ranjan.androidnewlearnings.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.ranjan.androidnewlearnings.model.ApiInterface
import dev.ranjan.androidnewlearnings.model.local.DatabaseTable
import dev.ranjan.androidnewlearnings.model.local.RoomDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TheViewModel(private val dao: RoomDao, private val apiInterface: ApiInterface) : ViewModel() {

    private val list = MutableLiveData<List<DatabaseTable>>()
    private val apiData = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            dao.getData().collect {
                list.postValue(it)
            }
        }
    }

    fun callSomeData(): LiveData<List<DatabaseTable>> {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertData(DatabaseTable("ranjan"))
        }
        return list
    }

    fun deleteData(id: Int) {
        viewModelScope.launch {
            dao.delete(id)
        }
    }

    fun doApiCall(): LiveData<String> {
        viewModelScope.launch {
            apiData.postValue(apiInterface.doApiCall().toString())
        }
        return apiData
    }
}