package dev.ranjan.socialmedia.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Room Entity")
data class RoomEntity(
    @ColumnInfo(name = "a") val a: Int,
    @ColumnInfo(name = "b") val b: Int,
    @ColumnInfo(name = "c") val c: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}