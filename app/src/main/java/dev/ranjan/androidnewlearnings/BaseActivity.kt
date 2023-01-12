package dev.ranjan.androidnewlearnings

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.*

open class BaseActivity : AppCompatActivity() {

    private var time = 10
    private var flag = false

    override fun onResume() {
        super.onResume()
        if (flag) logoutOperation()
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        Log.d("TAG", "onUserInteraction: ")
        resetTimer()
    }

    private fun resetTimer() {
        time = 10
    }

    companion object {
        private var job: Job? = null
    }

    fun startTimer() {
        job = CoroutineScope(Dispatchers.IO).launch {
            do {
                delay(1000)
                --time
                Log.d("TAG", "startTimer: $time")
                if (time == 0 && lifecycle.currentState == Lifecycle.State.RESUMED) {
                    withContext(Dispatchers.Main) {
                        logoutOperation()
                    }
                } else {
                    flag = true
                }
            } while (time != 0)
        }
    }

    fun stopTimer() {
        job?.cancel()
    }

    private fun logoutOperation() {
        val intent = Intent(this@BaseActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        Toast.makeText(this@BaseActivity, "User Logout", Toast.LENGTH_SHORT).show()
    }
}
