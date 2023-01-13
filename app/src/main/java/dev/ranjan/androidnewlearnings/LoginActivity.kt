package dev.ranjan.androidnewlearnings

import android.content.Intent
import android.os.Bundle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dev.ranjan.androidnewlearnings.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        autoLogoutAction()
        binding.btn.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
    private fun autoLogoutAction() {
        stopTimer()
        val flag = intent.getBooleanExtra("logout", false)
        if (flag) {
            MaterialAlertDialogBuilder(this@LoginActivity).setCancelable(false)
                .setTitle("Please Login again!").setMessage("You are been logout due to inactivity")
                .setPositiveButton("OK") { _, _ ->
                }.show()
        }
    }
}