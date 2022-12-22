package dev.ranjan.androidnewlearnings

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dev.ranjan.socialmedia.di.NetworkModule

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}