package dev.ranjan.androidnewlearnings

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("TAG", "onStartCommand: Services Started")
        generateRandomNumber()
        return START_STICKY
    }

    /**
     * START_STICKY ->
     */

    lateinit var job: Job
    private fun generateRandomNumber() {
        job = CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                val randomNumber = (100000..999999).random()
                Log.d("TAG", "generateRandomNumber: $randomNumber")
                delay(1000)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        Log.d("TAG", "onDestroy: Service Stopped")
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("TAG", "onBind: Ob Bind")
        return mBinder
    }

    private val mBinder = MyBinder()


    inner class MyBinder : Binder() {
        fun getService(): MyService? {
            return this@MyService
        }
    }
}