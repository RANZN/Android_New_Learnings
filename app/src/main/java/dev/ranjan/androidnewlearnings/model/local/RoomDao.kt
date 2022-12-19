package dev.ranjan.androidnewlearnings.model.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert

@Dao
interface RoomDao {

    @Insert
    suspend fun insertData(database: DatabaseTable)

//    @Delete
//    suspend fun delete(key: Int?)

}