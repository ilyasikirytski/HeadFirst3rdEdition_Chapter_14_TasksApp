package com.example.chapter_14_tasksapp.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chapter_14_tasksapp.model.TaskDao

// TODO Task ID не очент передавать через конструктор, лучше через какой-то
//  мтеод типа `init(taskId)` / `load(taskId)` и форматирование кода
class EditTaskViewModelFactory(
    private val taskId: Long,
    private val dao: TaskDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditTaskViewModel::class.java)) {
            return EditTaskViewModel(taskId, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}