package dev.ranjan.androidnewlearnings.model

import dev.ranjan.androidnewlearnings.model.remote.ResponseDTO
import retrofit2.http.GET

interface ApiInterface {

    @GET("entries")
    suspend fun doApiCall(): ResponseDTO

}