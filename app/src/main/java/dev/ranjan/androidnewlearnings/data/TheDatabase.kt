package dev.ranjan.androidnewlearnings.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.ranjan.androidnewlearnings.data.entities.*
import dev.ranjan.androidnewlearnings.data.entities.relations.StudentAndSubjectCrossRel

@Database(
    entities = [
        Director::class,
        School::class,
        Student::class,
        Subject::class,
        StudentAndSubjectCrossRel::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class TheDatabase : RoomDatabase() {
    abstract val theDao: TheDao

    companion object {
        @Volatile
        private var INSTANCE: TheDatabase? = null

        fun getInstance(context: Context): TheDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext, TheDatabase::class.java, "the-database.db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}