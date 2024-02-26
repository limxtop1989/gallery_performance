package com.example.galleryperformance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import com.example.galleryperformance.viewmodel.TaskViewModel
import com.example.galleryperformance.viewmodel.TaskViewModelFactory

class MainActivity : AppCompatActivity() {

    private val taskViewModel: TaskViewModel by viewModels<TaskViewModel> {
        TaskViewModelFactory((application as PerformanceApp).taskRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val days: EditText = findViewById(R.id.days)
        val countPerDay: EditText = findViewById(R.id.countPerDay)
        val insert: Button = findViewById(R.id.insert)
        insert.setOnClickListener {
            val days = days.text
            val countPerDay = countPerDay.text
            if (TextUtils.isEmpty(days) || TextUtils.isEmpty(countPerDay)) {
                return@setOnClickListener
            }
            taskViewModel.insert(Integer.valueOf(days.toString()),
                Integer.valueOf(countPerDay.toString()))
        }
        val delete: Button = findViewById(R.id.delete)
        delete.setOnClickListener {
            taskViewModel.delete()
        }

        val monthGroupQuery: Button = findViewById(R.id.monthGroup)
        monthGroupQuery.setOnClickListener {
            taskViewModel.monthGroupQuery()
        }
        val dateGroupQuery: Button = findViewById(R.id.dateGroup)
        dateGroupQuery.setOnClickListener {
            taskViewModel.dateGroupQuery()
        }
        val rawQuery: Button = findViewById(R.id.rawQuery)
        rawQuery.setOnClickListener() {
            taskViewModel.queryAllTask()
        }
    }

}