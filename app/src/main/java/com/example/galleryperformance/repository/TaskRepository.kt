package com.example.galleryperformance.repository

import androidx.annotation.WorkerThread
import com.example.galleryperformance.database.dao.TaskDao
import com.example.galleryperformance.entity.Count
import com.example.galleryperformance.entity.Task
import java.util.*

class TaskRepository(private val taskDao: TaskDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
//    val words: Flow<MutableMap<Task, Word>> = planDao.getTasks(Date())

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
//    @Suppress("RedundantSuspendModifier")
//    @WorkerThread
//    suspend fun insert(word: Plan) {
//        planDao.insert(word)
//    }


    /**
     * The words may be scheduled at any time of today, especially later than now, to query these out,
     * we need to offset the time to the end of day.
     */
    private fun getEndOfDate(): Date {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        // Set to the end of the day
        calendar[Calendar.HOUR_OF_DAY] = 23
        calendar[Calendar.MINUTE] = 59
        calendar[Calendar.SECOND] = 59
        calendar[Calendar.MILLISECOND] = 999
        return calendar.time
    }

    @WorkerThread
    suspend fun monthGroupQuery(): List<Count> {
        return taskDao.getMonthGroupTasks()
    }

    @WorkerThread
    suspend fun dateGroupQuery(): List<Count> {
        return taskDao.getDateGroupTasks()
    }

    @WorkerThread
    suspend fun queryAllTask(): List<Count> {
        return taskDao.getAllTasks()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(tasks: List<Task>) {
        taskDao.insert(tasks)
    }

    @WorkerThread
    suspend fun update(task: Task) {
        taskDao.update(task)
    }

    @WorkerThread
    suspend fun delete() {
        taskDao.delete()
    }

}