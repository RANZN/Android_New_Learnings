package dev.ranjan.socialmedia.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.ranjan.socialmedia.data.local.model.RoomEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface RoomDao {

    @Insert
    suspend fun insertData(roomEntity: RoomEntity)

    @Query("select * from `room entity`")
    fun getData(): LiveData<List<RoomEntity>>

    @Query("select SUM(a) from `room entity`")
    fun getFirstCount(): Flow<Int>

    @Query("select SUM(b) from `room entity`")
    fun getSecondCount(): Flow<Int>

    @Delete
    fun deleteData(roomEntity: RoomEntity)
}