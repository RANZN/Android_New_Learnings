package dev.ranjan.androidnewlearnings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dev.ranjan.androidnewlearnings.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var serviceIntent: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        serviceIntent = Intent(applicationContext, MyService::class.java)
        binding.startBtn.setOnClickListener {
            startService(serviceIntent)
        }

        binding.stopBtn.setOnClickListener {
            stopService(serviceIntent)
        }

    }
}

