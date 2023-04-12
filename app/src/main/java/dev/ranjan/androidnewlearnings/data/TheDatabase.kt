package dev.ranjan.androidnewlearnings.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class TheDatabase : RoomDatabase() {
    abstract val theDao: TheDao

    companion object {
        @Volatile
        private var INSTANCE: TheDatabase? = null

        fun getInstance(context: Context): TheDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext, TheDatabase::class.java, "the_database"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}