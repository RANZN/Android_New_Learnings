package dev.ranjan.androidnewlearnings.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
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

    // @params BASE_URL defined in BuildConfig file through build.gradle(module)
    @Provides
    @Singleton
    // @Named("Normal") // use where you have to use different Retrofit object.
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder().baseUrl("")
            .addConverterFactory(gsonConverterFactory).client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor, chuck: ChuckerInterceptor
    ): OkHttpClient {
        return if (BuildConfig.DEBUG) OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .addInterceptor(chuck).build()
        //writeTimeout().readTimeout().build()
        else OkHttpClient.Builder().build()
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
    fun providesApiInterface(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}