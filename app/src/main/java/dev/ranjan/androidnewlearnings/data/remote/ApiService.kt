package dev.ranjan.androidnewlearnings.data.remote

import dev.ranjan.androidnewlearnings.data.ResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("api/npm-covid-data/")
    suspend fun postApi(): Response<List<ResponseItem>>

}