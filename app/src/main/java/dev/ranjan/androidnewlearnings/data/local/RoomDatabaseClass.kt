package dev.ranjan.socialmedia.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.ranjan.socialmedia.data.local.model.RoomEntity

@Database(entities = [RoomEntity::class], version = 1)
abstract class RoomDatabaseClass : RoomDatabase() {
    abstract fun getRoomDao(): RoomDao

//    companion object {
//        private val INSTANCE: RoomDatabaseClass? = null
//
//        fun getDatabase(context: Context): RoomDatabaseClass {
//            return INSTANCE ?: Room.databaseBuilder(context, RoomDatabaseClass::class.java, "RoomDatabaseName")
//                .fallbackToDestructiveMigration().build()
//        }
//    }
}