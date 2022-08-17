package com.example.chapter_14_tasksapp.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chapter_14_tasksapp.data.TaskEntity
import com.example.chapter_14_tasksapp.model.TaskDao
import kotlinx.coroutines.launch

//
// TODO те жу вопросы по получниею из базы в основном потоке что и EditTaskViewModel
class TasksViewModel(val dao: TaskDao) : ViewModel() {
    val tasks = dao.getAll()

    // TOD лайв дату обычно стараются избегать делать нуллабл, лучше подумать и сделать просто Long
    private val _navigateToTask = SingleLiveEvent<Long>()
    val navigateToTask: SingleLiveEvent<Long>
        get() = _navigateToTask

    // TOD имя должн передаваться прямо в этот метод и через конструктор в task(entity)
    fun addTask(taskName: String) {
        viewModelScope.launch {
            val taskEntity = TaskEntity(taskName)
            dao.insert(taskEntity)
        }
    }

    fun onTaskClicked(taskId: Long) {
        _navigateToTask.value = taskId
    }
}