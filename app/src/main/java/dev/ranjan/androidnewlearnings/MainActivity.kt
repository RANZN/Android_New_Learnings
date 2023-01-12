package dev.ranjan.androidnewlearnings

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dev.ranjan.androidnewlearnings.encrypted_storage.EncryptedRoomDatabase
import dev.ranjan.androidnewlearnings.encrypted_storage.room.DatabaseTable
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val database = EncryptedRoomDatabase.buildDatabase(BuildConfig.password, this)
        val dao = database.getDao()

        CoroutineScope(Dispatchers.IO).launch {
            for (i in 0..10)
                dao.insertData(DatabaseTable("Ranjan"))
        }

        CoroutineScope(Dispatchers.IO).launch {
            delay(5000)
            withContext(Dispatchers.Main) {
                dao.getData().observe(this@MainActivity){
                    Log.d("TAG", "onCreate: $it")
                }
            }
        }

    }
}