package dev.ranjan.androidnewlearnings.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.ranjan.androidnewlearnings.data.local.RoomDao
import dev.ranjan.androidnewlearnings.data.local.model.RoomEntity

@Database(entities = [RoomEntity::class], version = 1)
abstract class RoomDatabaseClass : RoomDatabase() {
    abstract fun getRoomDao(): RoomDao

    companion object {

        fun getDatabase(context: Context): RoomDatabaseClass {
            return Room.databaseBuilder(context, RoomDatabaseClass::class.java, "RoomDatabaseName")
                .fallbackToDestructiveMigration().build()
        }
    }


//    companion object {
//        val migration = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("SELECT * FROM `tableName`")
//            }
//        }
//        val migration2 = object : Migration(2, 3) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//
//            }
//        }
//    }
}