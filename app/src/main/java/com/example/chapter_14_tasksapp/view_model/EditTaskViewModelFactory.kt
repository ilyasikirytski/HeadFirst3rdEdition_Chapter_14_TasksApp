package com.example.chapter_14_tasksapp.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chapter_14_tasksapp.data.TaskEntity
import com.example.chapter_14_tasksapp.model.TaskDao
import com.example.chapter_14_tasksapp.model.TaskDatabase

// TODO Task ID не очент передавать через конструктор, лучше через какой-то
//  мтеод типа `init(taskId)` / `load(taskId)` и форматирование кода
class EditTaskViewModelFactory(
    context: Context
) : ViewModelProvider.Factory {

    val taskDatabase = TaskDatabase.getInstance(context.applicationContext)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditTaskViewModel::class.java)) {
            return EditTaskViewModel(taskDatabase.taskDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}