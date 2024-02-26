package com.example.galleryperformance.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.galleryperformance.entity.Task
import com.example.galleryperformance.repository.TaskRepository
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val allTasks: MutableLiveData<List<Task>> = MutableLiveData()

    val count: MutableLiveData<Int> = MutableLiveData<Int>(0)

    fun monthGroupQuery() = viewModelScope.launch {
        val startTime = System.nanoTime()
        val result = repository.monthGroupQuery()
        val estimatedTime = System.nanoTime() - startTime
        Log.d("TaskViewModel: ", "monthGroupQuery: ${estimatedTime / 1000_000}, size:${result.size}")
//        allTasks.value = result
    }

    fun dateGroupQuery() = viewModelScope.launch {
        val startTime = System.nanoTime()
        val result = repository.dateGroupQuery()
        val estimatedTime = System.nanoTime() - startTime
        Log.d("TaskViewModel: ", "dateGroupQuery: ${estimatedTime / 1000_000}, size:${result.size}")
//        allTasks.value = result
    }

    fun queryAllTask() = viewModelScope.launch {
        val startTime = System.nanoTime()
        val result = repository.queryAllTask()
        val estimatedTime = System.nanoTime() - startTime
        Log.d("TaskViewModel: ", "queryAllTask: ${estimatedTime / 1000_000}, size:${result.size}")
//        allTasks.value = result
    }

    fun insert(days: Int, countPerDay: Int) = viewModelScope.launch {
        val tasks: MutableList<Task> = mutableListOf()
        val calendar = Calendar.getInstance()
        for (i in 1 .. days) {
            for (j in 1 .. countPerDay) {
                tasks.add(Task(calendar.time))
            }
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        repository.insert(tasks)
    }

    fun delete() = viewModelScope.launch {
        repository.delete()
    }


    private fun nextTime(task: Task): Date {
        val count = task.skim
        val fibonacci = fibonacci(count!!)
        val calendar = Calendar.getInstance()
//        calendar.set(task.startTime.year, task.startTime.month, task.startTime.date) wrong.
        calendar.add(Calendar.DAY_OF_YEAR, fibonacci.toInt())
        return calendar.time
    }

    /**
     * return the number indexed at n.
     */
    private fun fibonacci(n: Int): Long {
        val result : Double = Math.pow((( 1 + Math.sqrt(5.0)) / 2), n.toDouble()) / Math.sqrt(5.0)
        return Math.round(result)
    }

}

class TaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

