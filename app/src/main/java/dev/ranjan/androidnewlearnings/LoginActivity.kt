package dev.ranjan.androidnewlearnings

import android.content.Intent
import android.os.Bundle
import dev.ranjan.androidnewlearnings.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        stopTimer()
        binding.btn.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}