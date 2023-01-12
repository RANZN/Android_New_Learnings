package dev.ranjan.androidnewlearnings

import android.content.Intent
import android.os.Bundle
import dev.ranjan.androidnewlearnings.databinding.ActivityDashboardBinding

class DashboardActivity : BaseActivity() {
    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startTimer()

        binding.logoutBtn.setOnClickListener {
            finishAffinity()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}