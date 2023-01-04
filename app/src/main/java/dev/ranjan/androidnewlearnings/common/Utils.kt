package dev.ranjan.androidnewlearnings.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> {
    return this
}

fun <T> Flow<T>.asLiveData(): LiveData<T> {
    val liveData = MutableLiveData<T>()
    CoroutineScope(Dispatchers.IO).launch {
        this@asLiveData.collect {
            liveData.postValue(it)
        }
    }
    return liveData.asLiveData()
}

fun Context.hasNetwork(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            //for check internet over Bluetooth
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    } else {
        @Suppress("DEPRECATION") return connectivityManager.activeNetworkInfo?.isConnected ?: false
    }
}

fun Context.isConnectedToNetwork(): Internet {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return Internet.NOT_CONNECTED
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return Internet.NOT_CONNECTED
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
            // phone is connected to a network with internet access
            Internet.CONNECTED
        } else {
            // phone is connected to a network but does not have internet access
            Internet.CONNECTED_WITH_NO_INTERNET
        }
    } else {
        @Suppress("DEPRECATION")
        if (connectivityManager.activeNetworkInfo?.isConnected == true) Internet.CONNECTED else Internet.NOT_CONNECTED
    }
}

enum class Internet {
    CONNECTED, NOT_CONNECTED, CONNECTED_WITH_NO_INTERNET
}