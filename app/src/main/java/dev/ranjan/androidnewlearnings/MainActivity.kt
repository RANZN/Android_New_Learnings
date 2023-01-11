package dev.ranjan.androidnewlearnings

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import dev.ranjan.androidnewlearnings.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var localBroadcastManager: LocalBroadcastManager

    private val receiver = MyBroadcastReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        localBroadcastManager = LocalBroadcastManager.getInstance(this)

        val intentFilter = IntentFilter("dev.ranjan.sendBroadcast")
        localBroadcastManager.registerReceiver(receiver, intentFilter)

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.putExtra(Intent.EXTRA_TITLE,"RANJAN")
        shareIntent.putExtra(Intent.EXTRA_TEXT,"PRAKASH")
        shareIntent.type = "image/";
        val activities: List<ResolveInfo> = this.packageManager.queryIntentActivities(
            shareIntent, 0
        )
        Log.d("TAG", "onCreate: $activities")

        activities.forEach {
            Log.d("TAG", "onCreate: ${it.activityInfo.applicationInfo}")
        }
        startActivity(Intent.createChooser(shareIntent, "Share intent"))
    }

    override fun onResume() {
        super.onResume()
        binding.sendBroadcastButton.setOnClickListener {
            val newIntent = Intent("dev.ranjan.sendBroadcast")
            newIntent.putExtra("data", "RANJAN")
            localBroadcastManager.sendBroadcast(newIntent)
        }
        binding.btActivity.setOnClickListener {
            val intent = Intent(this, WifiActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        localBroadcastManager.unregisterReceiver(receiver)
    }

    inner class MyBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val data = intent.getStringExtra("data")
            binding.txt.text = data
        }
    }
}