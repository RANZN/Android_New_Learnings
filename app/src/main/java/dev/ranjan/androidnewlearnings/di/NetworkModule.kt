package dev.ranjan.androidnewlearnings.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.ranjan.androidnewlearnings.BuildConfig
import dev.ranjan.androidnewlearnings.data.remote.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient,
    ): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(gsonConverterFactory).client(okHttpClient)
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideCustomGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()

    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        chuck: ChuckerInterceptor,
    ): OkHttpClient {
        val okhttp = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) okhttp.addInterceptor(httpLoggingInterceptor).addInterceptor(chuck)

//        okhttp.connectTimeout(1, TimeUnit.MINUTES).writeTimeout(30, TimeUnit.SECONDS)
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

}