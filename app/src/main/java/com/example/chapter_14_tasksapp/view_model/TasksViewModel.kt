package com.example.chapter_14_tasksapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chapter_14_tasksapp.data.TaskEntity
import com.example.chapter_14_tasksapp.model.TaskDao
import com.example.chapter_14_tasksapp.view.MainActivity
import kotlinx.coroutines.launch
//
// TODO те жу вопросы по получниею из базы в основном потоке что и EditTaskViewModel
class TasksViewModel(val dao: TaskDao) : ViewModel() {
    var newTaskName = ""
    val tasks = dao.getAll()


    // TODO лайв дату обычно стараются избегать делать нуллабл, лучше подумать и сделать просто Long
    private val _navigateToTask = SingleLiveEvent<Long>()/////
    val navigateToTask: SingleLiveEvent<Long>
        get() = _navigateToTask

    // TODO имя должн передаваться прямо в этот метод и через конструктор в task(entity)
    fun addTask(taskName: String) {
        viewModelScope.launch {
            val taskEntity = TaskEntity(taskName)
//            taskEntity.taskName = newTaskName
            dao.insert(taskEntity)
        }
    }

    fun onTaskClicked(taskId: Long) {
        _navigateToTask.value = taskId
    }

//    fun onTaskNavigated() {
//        // TOD а зачем тут обнулять вообще? и еще, почему тут просто liveData,
//        //  слышал что-нибудь про SingleLiveEvent?
////        _navigateToTask.call()
//    }
}