package dev.ranjan.androidnewlearnings.data.remote

import dev.ranjan.androidnewlearnings.common.TokenKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject


class CustomAuth @Inject constructor(private val apiService: ApiService, private val tokenKey: TokenKey) :
    Authenticator {
    override fun authenticate(route: Route?, response: Response): Request {

        val newAccessToken = runBlocking(Dispatchers.IO) { apiService.getRefreshToken() }
        tokenKey.TOKEN=newAccessToken.body()?.accessToken.toString() // save new access_token for next called
        return response.request.newBuilder()
            .header(
                "Authorization",
                newAccessToken.body()?.accessToken.toString()
            ) // just only need to override "Authorization" header, don't need to override all header since this new request is create base on old request
            .build()
    }

}