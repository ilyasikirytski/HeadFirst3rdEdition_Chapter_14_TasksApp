package com.example.chapter_14_tasksapp.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chapter_14_tasksapp.model.TaskDatabase

class EditTaskViewModelFactory(
    context: Context
) : ViewModelProvider.Factory {

    private val taskDatabase = TaskDatabase.getInstance(context.applicationContext)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditTaskViewModel::class.java)) {
            return EditTaskViewModel(taskDatabase.taskDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}