package com.example.chapter_14_tasksapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chapter_14_tasksapp.data.TaskEntity
import com.example.chapter_14_tasksapp.model.TaskDao
import kotlinx.coroutines.launch


class EditTaskViewModel(private val dao: TaskDao) : ViewModel() {

    var task: LiveData<TaskEntity> = MutableLiveData()

    fun setText(savedTextState: String?): String {
        var text = ""
        if (savedTextState == null && task.value != null) {
            text = task.value!!.taskName
        } else if (savedTextState != null) {
            text = savedTextState
        }
        return text
    }

    fun setIsChecked(savedStateBoolean: Boolean?): Boolean {
        var isChecked = false
        if (savedStateBoolean == null && task.value != null) {
            isChecked = task.value!!.taskDone
        } else if (savedStateBoolean != null) {
            isChecked = savedStateBoolean
        }
        return isChecked
    }

    fun load(taskId: Long): LiveData<TaskEntity> {
        viewModelScope.launch {
            task = dao.getById(taskId)
        }
        return task
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
            dao.delete(task.value!!)
            _navigateToList.value = true
        }
    }
}