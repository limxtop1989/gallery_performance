package com.example.galleryperformance.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.galleryperformance.database.dao.TaskDao
import com.example.galleryperformance.entity.Task
import com.maxim.wordcard.database.converter.DateConverter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(
    version = 1,
    entities = [Task::class],
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            val db = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "performance"
            ).addTypeConverter(DateConverter())
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
//                        GlobalScope.launch {
//                            getInstance(context).courseDao().insert(insertBuiltInCourses(context))
//                        }
                    }
                })
                .build()


            return db
        }
    }

}