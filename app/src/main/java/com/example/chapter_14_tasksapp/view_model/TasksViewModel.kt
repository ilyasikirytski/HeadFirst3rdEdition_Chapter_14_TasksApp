package com.example.chapter_14_tasksapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chapter_14_tasksapp.data.TaskEntity
import com.example.chapter_14_tasksapp.model.TaskDao
import kotlinx.coroutines.launch

class TasksViewModel(private val dao: TaskDao) : ViewModel() {

    var tasks: LiveData<List<TaskEntity>>? = null
    fun load() {
        viewModelScope.launch {
            tasks = dao.getAll()
        }
    }

    private val _navigateToTask = SingleLiveEvent<Long>()
    val navigateToTask: SingleLiveEvent<Long>
        get() = _navigateToTask

    fun addTask(taskName: String) {
        viewModelScope.launch {
            if (taskName.isEmpty()) {
                dao.insert(TaskEntity("Default Name"))
            } else {
                dao.insert(TaskEntity(taskName))
            }
        }
    }

    fun onTaskClicked(taskId: Long) {
        _navigateToTask.value = taskId
    }
}