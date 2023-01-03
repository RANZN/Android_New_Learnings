package dev.ranjan.androidnewlearnings.data

import dev.ranjan.androidnewlearnings.common.safeApiCall
import dev.ranjan.androidnewlearnings.data.local.RoomDao
import dev.ranjan.androidnewlearnings.data.remote.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

class Repository @Inject constructor(private val dao: RoomDao, private val apiService: ApiService) {


    suspend fun doApiCall() = safeApiCall { apiService.postApi() }

}

