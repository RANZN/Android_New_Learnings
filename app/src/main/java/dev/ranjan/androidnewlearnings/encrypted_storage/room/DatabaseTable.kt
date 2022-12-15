package dev.ranjan.androidnewlearnings.encrypted_storage.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Some Table")
data class DatabaseTable(val name: String) {

    @PrimaryKey(autoGenerate = true)
    var key: Int? = null
}
