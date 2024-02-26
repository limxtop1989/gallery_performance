package com.example.galleryperformance

import android.app.Application
import com.example.galleryperformance.database.AppDatabase
import com.example.galleryperformance.repository.TaskRepository

class PerformanceApp : Application() {

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getInstance(this) }
    val taskRepository by lazy { TaskRepository(database.taskDao()) }


}