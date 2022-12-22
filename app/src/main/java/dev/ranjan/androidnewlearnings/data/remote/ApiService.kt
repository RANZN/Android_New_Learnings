package dev.ranjan.socialmedia.data.remote

import dev.ranjan.socialmedia.data.ResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("credit/x/api/v1.0/dynamic/page/fields")
    suspend fun postApi(): Response<List<ResponseItem>>

}