package dev.ranjan.androidnewlearnings

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import dev.ranjan.androidnewlearnings.LoginStatus.*
import dev.ranjan.androidnewlearnings.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: Database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = Database()

        binding.loginButton.setOnClickListener {
            binding.emailLayout.error = null
            binding.passwordLayout.error = null
            val loginStatus = database.login(
                email = binding.email.text.toString(), password = binding.password.text.toString()
            )
            when (loginStatus) {
                SUCCESS -> Toast.makeText(this, SUCCESS.value, Toast.LENGTH_SHORT).show()
                WRONG_PASSWORD -> showError(WRONG_PASSWORD.value, binding.passwordLayout)
                USER_NOT_FOUND -> showError(USER_NOT_FOUND.value, binding.emailLayout)
                ENTER_DETAILS -> showError(
                    ENTER_DETAILS.value, binding.emailLayout, binding.passwordLayout
                )
                ENTER_PASSWORD -> showError(ENTER_PASSWORD.value, binding.passwordLayout)
                INVALID_EMAIL -> showError(INVALID_EMAIL.value, binding.emailLayout)
            }
        }
    }

    private fun showError(body: String, vararg views: TextInputLayout) {
        views.forEach { view ->
            view.error = body
        }
    }

}