package dev.ranjan.androidnewlearnings.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.ranjan.androidnewlearnings.data.local.RoomDao
import dev.ranjan.androidnewlearnings.data.local.RoomDatabaseClass
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideDao(roomDatabaseClass: RoomDatabaseClass): RoomDao = roomDatabaseClass.getRoomDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RoomDatabaseClass {
        return Room.databaseBuilder(
            context, RoomDatabaseClass::class.java, "RoomDatabaseName"
        )/*.addMigrations(migration)*/.fallbackToDestructiveMigration().build()
    }
    
//    companion object {
//        val migration = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("SELECT * FROM `tableName`")
//            }
//        }
//        val migration2 = object : Migration(2, 3) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//
//            }
//        }
//    }

}