package com.example.chapter_14_tasksapp.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chapter_14_tasksapp.model.TaskDatabase

class TaskViewModelFactory(
    context: Context
) : ViewModelProvider.Factory {

    private val database = TaskDatabase.getInstance(context.applicationContext)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksViewModel::class.java)) {
            return TasksViewModel(database.taskDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}