package dev.ranjan.androidnewlearnings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.ranjan.androidnewlearnings.encrypted_storage.EncryptedRoomDatabase
import dev.ranjan.androidnewlearnings.encrypted_storage.room.DatabaseTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val database = EncryptedRoomDatabase.buildDatabase("ranjan", this)
        val dao = database.getDao()

        CoroutineScope(Dispatchers.IO).launch {
            dao.insertData(DatabaseTable("Ranjan"))
        }
    }
}