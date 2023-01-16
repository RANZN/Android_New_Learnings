package dev.ranjan.androidnewlearnings.data.remote

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("upload")
    suspend fun upload(@Part body: MultipartBody.Part): Response<String>

    @GET("/")
    suspend fun check(): Any
}