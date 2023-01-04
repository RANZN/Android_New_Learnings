package dev.ranjan.androidnewlearnings.data.remote

import dev.ranjan.androidnewlearnings.data.remote.dto.AssetTicker
import dev.ranjan.androidnewlearnings.data.remote.dto.TokenResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    /**
     * Adding or removing headers at runtime check NetworkModule.kt
     */

    //    @Headers("content-type", "application/json")
    @POST("oauth/token")
    @Headers("remove-content-type:false")
    suspend fun getToken(@Body body: RequestBody): Response<TokenResponse>

    @Headers("remove-content-type:true")
    @GET("market-cap?assetId=%20f1ff77b6-3ab4-4719-9ded-2fc7e71cff1f")
    suspend fun getData(): Response<AssetTicker>

}