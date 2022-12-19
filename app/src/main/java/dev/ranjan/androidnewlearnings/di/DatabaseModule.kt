package dev.ranjan.androidnewlearnings.di

import android.content.Context
import androidx.room.Room
import dev.ranjan.androidnewlearnings.model.EncryptedRoomDatabase
import org.koin.dsl.module

/*
Here define the creation of RoomDatabase and getting instance of Dao.
 */
val databaseModule = module {
    single {
//        val supportFactory = SupportFactory(SQLiteDatabase.getBytes("ranjan".toCharArray()))
        return@single Room.databaseBuilder(
            get<Context>(), EncryptedRoomDatabase::class.java, "RoomDatabase"
        )/*  .openHelperFactory(supportFactory)*/.fallbackToDestructiveMigration().build()
    }

    single {
        return@single get<EncryptedRoomDatabase>().getDao()
    }
}
