package dev.ranjan.androidnewlearnings.di

import android.content.Context
import android.content.Intent
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.ranjan.androidnewlearnings.BuildConfig
import dev.ranjan.androidnewlearnings.MainActivity
import dev.ranjan.androidnewlearnings.common.TokenKey
import dev.ranjan.androidnewlearnings.common.hasNetwork
import dev.ranjan.androidnewlearnings.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    // @params BASE_URL defined in BuildConfig file through build.gradle(module)
    @Provides
    @Singleton
    // @Named("Normal") // use where you have to use different Retrofit object.
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient,
    ): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(gsonConverterFactory).client(okHttpClient)
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        chuck: ChuckerInterceptor,
        @Named("custom_header") custom_header_Interceptor: Interceptor,
        @Named("caching") offlineInterceptor: Interceptor,
        @ApplicationContext context: Context,
        authenticator: Authenticator
    ): OkHttpClient {
        val cacheSize = (15 * 1024 * 1024).toLong() //15MB
        val myCache = Cache(context.cacheDir, cacheSize)

        val okhttp = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) okhttp.addInterceptor(httpLoggingInterceptor).addInterceptor(chuck)

        okhttp/*.cache(myCache)*/.addNetworkInterceptor(custom_header_Interceptor)
//            .authenticator(authenticator)

//            .addInterceptor(offlineInterceptor)
//            .connectTimeout(1, TimeUnit.MINUTES)
//            .writeTimeout(30, TimeUnit.SECONDS)
//            .readTimeout(15, TimeUnit.SECONDS)

        return okhttp.build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideChuckInterceptor(@ApplicationContext context: Context): ChuckerInterceptor =
        ChuckerInterceptor.Builder(context).alwaysReadResponseBody(true).build()

    @Provides
    fun providesApiInterface(retrofitBuilder: Retrofit.Builder): ApiService =
        retrofitBuilder.build().create(ApiService::class.java)


    @Named("custom_header")
    @Provides
    fun provideCustomInterceptor(
        apiService: ApiService, tokenKey: TokenKey, @ApplicationContext context: Context
    ): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()

            val shouldRemoveContentType = original.headers["remove-content-type"] == "true"

            val request = original.newBuilder().addHeader("content-type", "application/json")
                .addHeader("X-RapidAPI-Key", BuildConfig.X_API_KEY)
                .addHeader("X-RapidAPI-Host", "bravenewcoin.p.rapidapi.com").addHeader(
                    "Authorization", "Bearer ${tokenKey.TOKEN}"
                ) //assuming tokenKey as a preference helper to save key

            if (shouldRemoveContentType) {
                request.removeHeader("content-type")
                request.removeHeader("remove-content-type")
            }

            val response = chain.proceed(request.build())
            when (response.code) {
                401 -> { //Forbidden
                    //do api call
                    val newResponse = runBlocking(Dispatchers.IO) {
                        val newTokenResponse = apiService.getRefreshToken()
                        tokenKey.TOKEN = newTokenResponse.body()?.accessToken.toString()
                        request.removeHeader("Authorization")
                            .addHeader("Authorization", "Bearer ${tokenKey.TOKEN}")
                    }
                    return@Interceptor chain.proceed(newResponse.build())
                }
                404/*NotFound */,
                403/*Bad Request*/ -> {
                    //assuming 403 & 404 is response for sign-out to the user.
                    val intent = Intent(context, MainActivity::class.java)
                    tokenKey.TOKEN = "" //clearing the token from preference
                    context.startActivity(intent) //logging off for user.

                    return@Interceptor response
                }
                else -> {
                    return@Interceptor response
                }
            }

        }
    }




    @Named("caching")
    @Provides
    fun provideCachingInterceptor(@ApplicationContext context: Context): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()

            //TODO("Clear these later--> maxAge, maxStale, onlyIfCached()")
            val cacheControl = CacheControl.Builder()
            val forNetwork = cacheControl.maxAge(0, TimeUnit.SECONDS).build()

            /**
             * maxAge-> Time after which network call will be done
             * maxStale-> Time period for which the data will be cached.
             */

            val forOffline = cacheControl/*.onlyIfCached()*/.maxStale(1, TimeUnit.DAYS).build()
            request = request.newBuilder().header(
                "Cache-Control", (if (context.hasNetwork()) forNetwork else forOffline).toString()
            ).build()

            chain.proceed(request)
        }
    }


}
