package dev.ranjan.androidnewlearnings

import android.util.Log
import androidx.lifecycle.ViewModel
import dev.ranjan.androidnewlearnings.common.Resource
import dev.ranjan.androidnewlearnings.common.asLiveData
import dev.ranjan.androidnewlearnings.data.Repository
import dev.ranjan.androidnewlearnings.data.local.RoomDao
import dev.ranjan.androidnewlearnings.data.remote.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class TheViewModel(
    private val dao: RoomDao, private val repository: Repository, val apiService: ApiService
) : ViewModel() {


//    fun doApiCall() {
//        viewModelScope.launch {
////            Log.d("ranjan", "doApiCall: ${apiService.postApi()}")
//        }
//
//        viewModelScope.launch(Dispatchers.IO) {
//            dao.insertData(RoomEntity(1, 2, "Ranjan"))
//        }
//    }

//
//    fun getFirstCount(): LiveData<Int> = dao.getFirstCount()
//
//    fun getSecondCount(): LiveData<Int> = dao.getSecondCount()


//
//    fun getMergedData(): LiveData<List<Int>> {
//        val liveData1 = getFirstCount()
//        val liveData2 = getSecondCount()
//        val liveDataMerger: MediatorLiveData<Int> = MediatorLiveData<Int>()
//        liveDataMerger.addSource(liveData1) {
//            list.add(0, it)
//            someLiveData.postValue(list)
//            liveDataMerger.setValue(it)
//        }
//        liveDataMerger.addSource(liveData2) {
//            list.add(1, it)
//            someLiveData.postValue(list)
//            liveDataMerger.setValue(it)
//        }
//        return someLiveData as LiveData<List<Int>>
//    }


    fun getLiveData() = repository.data.asLiveData()


    fun doAPiCall() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.doApiCall().collect {
                when (it) {
                    is Resource.Success -> {
                        Log.d("ranjan", "doAPiCall:  ${it.data}")
                    }
                    is Resource.Error -> {
                        Log.d("ranjan", "error:  ${it.message}")
                    }
                    is Resource.Loading -> {

                    }
                }
            }


            flow{
                emit(1)
                delay(10000)
                emit(2)
                delay(100)
                emit(3)
            }.collect{

            }
        }
    }


}