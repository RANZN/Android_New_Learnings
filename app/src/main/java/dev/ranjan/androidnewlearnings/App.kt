package dev.ranjan.androidnewlearnings

import android.app.Application
import dev.ranjan.androidnewlearnings.di.appModule
import dev.ranjan.androidnewlearnings.di.databaseModule
import dev.ranjan.androidnewlearnings.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule, databaseModule, viewModelModule)
        }
    }
}