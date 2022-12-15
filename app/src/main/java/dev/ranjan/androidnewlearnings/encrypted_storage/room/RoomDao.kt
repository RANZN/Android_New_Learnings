package dev.ranjan.androidnewlearnings.encrypted_storage.room

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface RoomDao {

    @Insert
    suspend fun insertData(database: DatabaseTable)
}