package dev.ranjan.androidnewlearnings.encrypted_storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.ranjan.androidnewlearnings.encrypted_storage.room.RoomDao

//https://medium.com/vmware-end-user-computing/securing-a-room-database-with-passcode-based-encryption-82ec670961e
//https://sonique6784.medium.com/protect-your-room-database-with-sqlcipher-on-android-78e0681be687

@Database(entities = [Database::class], version = 1)
abstract class EncryptedRoomDatabase : RoomDatabase() {
    companion object {
        private var INSTANCE: EncryptedRoomDatabase? = null

        fun buildDatabase(passcode: String, context: Context): EncryptedRoomDatabase {

            if (INSTANCE == null) {
//                val supportFactory = SupportFactory(SQLiteDatabase.getBytes(passcode.toCharArray()))

               INSTANCE= Room.databaseBuilder(context, EncryptedRoomDatabase::class.java, "RoomDatabase")/*  .openHelperFactory(supportFactory)*/.fallbackToDestructiveMigration().build()
            }
            return INSTANCE as EncryptedRoomDatabase
        }
    }

    abstract fun getDao(): RoomDao
}
