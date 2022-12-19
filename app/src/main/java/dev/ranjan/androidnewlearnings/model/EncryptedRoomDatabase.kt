package dev.ranjan.androidnewlearnings.model

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.ranjan.androidnewlearnings.model.local.DatabaseTable
import dev.ranjan.androidnewlearnings.model.local.RoomDao

//https://medium.com/vmware-end-user-computing/securing-a-room-database-with-passcode-based-encryption-82ec670961e
//https://sonique6784.medium.com/protect-your-room-database-with-sqlcipher-on-android-78e0681be687

@Database(entities = [DatabaseTable::class], version = 1)
abstract class EncryptedRoomDatabase : RoomDatabase() {

    abstract fun getDao(): RoomDao
}
