package dev.ranjan.androidnewlearnings.data

import dev.ranjan.androidnewlearnings.common.safeApiCall
import dev.ranjan.androidnewlearnings.data.local.RoomDao
import dev.ranjan.androidnewlearnings.data.remote.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class Repository @Inject constructor(private val dao: RoomDao, private val apiService: ApiService) {

    suspend fun doApiCall(file: File) = safeApiCall {
        val requestFile = RequestBody.create("*/*".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        apiService.upload(body)
    }
}

