package dev.ranjan.androidnewlearnings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.ranjan.androidnewlearnings.data.TheDatabase
import dev.ranjan.androidnewlearnings.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val dao = TheDatabase.getInstance(this).theDao
    }
}