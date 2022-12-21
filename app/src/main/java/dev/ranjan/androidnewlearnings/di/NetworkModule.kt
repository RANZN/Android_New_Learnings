package dev.ranjan.androidnewlearnings.di

import dev.ranjan.androidnewlearnings.BuildConfig
import dev.ranjan.androidnewlearnings.model.ApiInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        val builder = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(get<GsonConverterFactory>())
            .client(get<OkHttpClient>())
        return@single builder.build()
    }

    single {
        return@single GsonConverterFactory.create()
    }

    single {
        return@single OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        return@single HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    single {
        return@single get<Retrofit>().create(ApiInterface::class.java)
    }
}