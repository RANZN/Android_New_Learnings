package dev.ranjan.androidnewlearnings.data

import dev.ranjan.androidnewlearnings.common.Resource
import dev.ranjan.androidnewlearnings.common.safeApiCall
import dev.ranjan.androidnewlearnings.data.local.RoomDao
import dev.ranjan.androidnewlearnings.data.remote.ApiService
import dev.ranjan.androidnewlearnings.data.remote.dto.TokenResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class Repository @Inject constructor(private val dao: RoomDao, private val apiService: ApiService) {

    private val mediaType = "application/json".toMediaTypeOrNull()
    private val body = "{\"audience\": \"https://api.bravenewcoin.com\",\"client_id\": \"oCdQoZoI96ERE9HY3sQ7JmbACfBf55RY\",\"grant_type\": \"client_credentials\" }"
        .toRequestBody(mediaType)

    suspend fun getToken(): Flow<Resource<TokenResponse>> = safeApiCall { apiService.getToken(body) }
    suspend fun getData() = safeApiCall { apiService.getData() }

}

