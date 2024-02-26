package com.example.galleryperformance.database.dao

import androidx.room.*
import com.example.galleryperformance.entity.Count
import com.example.galleryperformance.entity.Task

@Dao
interface TaskDao {

    @Query("select strftime('%Y', nextTime / 1000, 'unixepoch' ) as year, strftime('%m', nextTime / 1000, 'unixepoch' ) as month, \n" +
            "count(*) as count from task group by year, month order by year, month")
    suspend fun getMonthGroupTasks(): List<Count>

    @Query("select strftime('%Y', nextTime / 1000, 'unixepoch' ) as year, " +
            "strftime('%m', nextTime / 1000, 'unixepoch' ) as month, " +
            "strftime('%d', nextTime / 1000, 'unixepoch' ) as date, " +
            "count(*) as count from task group by year, month, date order by year, month, date")
    suspend fun getDateGroupTasks(): List<Count>

    @Query("select strftime('%Y', nextTime / 1000, 'unixepoch' ) as year, " +
            "strftime('%m', nextTime / 1000, 'unixepoch' ) as month, " +
            "strftime('%d', nextTime / 1000, 'unixepoch' ) as date " +
            "from task order by year, month, date")
    suspend fun getAllTasks(): List<Count>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(words: List<Task>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(task: Task)

    @Query("delete from task")
    suspend fun delete()

}