package com.example.chapter_14_tasksapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chapter_14_tasksapp.model.Task
import com.example.chapter_14_tasksapp.model.TaskDao
import kotlinx.coroutines.launch

// TODO те жу вопросы по получниею из базы в основном потоке что и EditTaskViewModel
class TasksViewModel(val dao: TaskDao) : ViewModel() {
    var newTaskName = ""
    val tasks = dao.getAll()

    // TODO лайв дату обычно стараются избегать делать нуллабл, лучше подумать и сделать просто Long
    private val _navigateToTask = MutableLiveData<Long?>()
    val navigateToTask: LiveData<Long?>
        get() = _navigateToTask

    // TODO имя должн передаваться прямо в этот метод и через конструктор в task
    fun addTask() {
        viewModelScope.launch {
            val task = Task()
            task.taskName = newTaskName
            dao.insert(task)
        }
    }

    fun onTaskClicked(taskId: Long) {
        _navigateToTask.value = taskId
    }

    fun onTaskNavigated() {
        // TODO а зачем тут обнулять вообще? и еще, почему тут просто liveData,
        //  слышал что-нибудь про SingleLiveEvent?
        _navigateToTask.value = null
    }
}