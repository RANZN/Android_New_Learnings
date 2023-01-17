package dev.ranjan.androidnewlearnings.data

import dev.ranjan.androidnewlearnings.common.ProgressCallback
import dev.ranjan.androidnewlearnings.common.ProgressRequestBody
import dev.ranjan.androidnewlearnings.common.uploadData
import dev.ranjan.androidnewlearnings.data.local.RoomDao
import dev.ranjan.androidnewlearnings.data.remote.ApiService
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

class Repository @Inject constructor(private val dao: RoomDao, private val apiService: ApiService) {

    suspend fun doApiCall(file: File, callback: ProgressCallback) = uploadData(callback) {
//        val requestFile = RequestBody.create("*/*".toMediaTypeOrNull(), file)
//        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        val progressBody = ProgressRequestBody(file, callback)
        val part = MultipartBody.Part.createFormData("file", file.name, progressBody)
        apiService.upload(part)
    }
}

