package dev.ranjan.socialmedia.data

import dev.ranjan.socialmedia.common.Resource
import dev.ranjan.socialmedia.common.safeApiCall
import dev.ranjan.socialmedia.data.local.RoomDao
import dev.ranjan.socialmedia.data.remote.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

class Repository @Inject constructor(private val dao: RoomDao, private val apiService: ApiService) {

    private val list = mutableListOf(0, 0)
    val data: Flow<MutableList<Int>> = dao.getFirstCount().zip(dao.getSecondCount()) { f1, f2 ->
        list[0] = f1
        list[1] = f2
        return@zip list
    }

    private fun getDataA(): Flow<List<Int>> {
        return combine(
            dao.getFirstCount(),
            dao.getSecondCount(),
            dao.getSecondCount(),
            dao.getSecondCount(),
            dao.getSecondCount(),
        ) { f1, f2, f3, f4, f5 ->
            list[0] = f1
            list[1] = f2
            list[2] = f3
            list[3] = f4
            return@combine list
        }
    }


    suspend fun doApiCall() = safeApiCall { apiService.postApi() }

    fun doSomethins(){
        CoroutineScope(Dispatchers.IO).launch {
            safeApiCall { apiService.postApi() }
        }
    }


}

