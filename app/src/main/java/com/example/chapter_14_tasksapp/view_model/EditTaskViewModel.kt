package com.example.chapter_14_tasksapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chapter_14_tasksapp.data.TaskEntity
import com.example.chapter_14_tasksapp.model.TaskDao
import kotlinx.coroutines.launch


class EditTaskViewModel(private val dao: TaskDao) : ViewModel() {

    var task: LiveData<TaskEntity>? = null

    fun load(taskId: Long) {
        viewModelScope.launch {
            task = dao.getById(taskId)
        }
    }

    private val _navigateToList = SingleLiveEvent<Boolean>()

    val navigateToList get() = _navigateToList

    fun updateTask(taskEntity: TaskEntity) {
        viewModelScope.launch {
            dao.update(taskEntity)
            _navigateToList.value = true
        }
    }

    fun deleteTask() {
        viewModelScope.launch {
            dao.delete(task?.value!!)
            _navigateToList.value = true
        }
    }
}