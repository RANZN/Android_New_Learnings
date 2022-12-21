package dev.ranjan.androidnewlearnings.model.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {

    @Insert
    suspend fun insertData(database: DatabaseTable)

    @Query("select * from `some table`")
    fun getData(): Flow<List<DatabaseTable>>

    @Query("delete from `some table`where `key`=:key")
    suspend fun delete(key: Int)

}