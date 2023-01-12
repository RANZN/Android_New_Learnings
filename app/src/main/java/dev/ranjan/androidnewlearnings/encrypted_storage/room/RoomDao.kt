package dev.ranjan.androidnewlearnings.encrypted_storage.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RoomDao {

    @Insert
    suspend fun insertData(database: DatabaseTable)

    @Query("select * from `some table`")
    fun getData(): LiveData<List<DatabaseTable>>
}