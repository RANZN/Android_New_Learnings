package dev.ranjan.androidnewlearnings

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import dev.ranjan.androidnewlearnings.databinding.ActivityWifiBinding

class WifiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWifiBinding

    private lateinit var wifiManager: WifiManager
    lateinit var results: MutableList<ScanResult>
    var flag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWifiBinding.inflate(layoutInflater)
        setContentView(binding.root)



        wifiManager = this.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        registerWifiReceiver()

        //For Manual Scan and checking all results

        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_WIFI_STATE
                    ), 101
                )
            }
            return
        }

        binding.getData.setOnClickListener {
            results = wifiManager.scanResults
            Log.d("ranjan", "onCreate: $results")
            setText()
        }
    }

    inner class WifiReceiver : BroadcastReceiver() {

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onReceive(context: Context, intent: Intent) {
            val success: Boolean = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
            //Automatically getting wifi changes
            if (success) {
                scanSuccess()
            } else {
                scanFailure()
            }
        }
    }

    private val receiver = WifiReceiver()
    private fun registerWifiReceiver() {
        registerReceiver(receiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    /**
     * For automatically getting wifi data
     */
    @SuppressLint("MissingPermission")
    private fun scanSuccess() {
        results = wifiManager.scanResults
        setText()
    }

    @SuppressLint("MissingPermission")
    private fun scanFailure() {
        results = wifiManager.scanResults
        setText()
    }

    private fun setText() {
        var str = "Available WiFi: \n"
        for (i in 0 until results.size) {
            str += results[i].SSID.toString() + "\n"
        }
        binding.tvWifi.text = str.toString()
    }
}