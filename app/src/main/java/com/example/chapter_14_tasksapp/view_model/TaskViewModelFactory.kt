package com.example.chapter_14_tasksapp.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chapter_14_tasksapp.model.TaskDatabase

// TODO лучше запихнуть сразу базу, а лучше вообще класс который будет создавать эту базу -
//  вьюмоделек может быть очень много, представь сколько ты сюда строчек пробрасывать будешь?
//  если у каждой хотя бы по одной зависимости, а если у каждой по 10?
class TaskViewModelFactory(
    private val database: TaskDatabase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksViewModel::class.java)) {
            return TasksViewModel(database.taskDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}