package com.example.chapter_14_tasksapp.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chapter_14_tasksapp.data.TaskEntity
import com.example.chapter_14_tasksapp.model.TaskDao
import kotlinx.coroutines.launch


class EditTaskViewModel(val dao: TaskDao) : ViewModel() {

    var taskId = 0L

    fun load(taskId: Long){
        this.taskId = taskId
    }
    // TODO  апочему получение из базы идет в основном потоке без корутин?
    var task = dao.getById(taskId)

    private val _navigateToList = SingleLiveEvent<Boolean>()

    // TODO а смысл если ты все равно ставишь тут Mutable? то есть вот так бы еще
    //  был бы какой-то смылсм val navigateToList: LiveData get() = _navigateToList
    val navigateToList get() = _navigateToList

    fun updateTask(taskEntity: TaskEntity) {
        viewModelScope.launch {
            dao.update(taskEntity)
            navigateToList.value = true
        }
    }

    fun deleteTask() {
        viewModelScope.launch {
            dao.delete(task.value!!)
            navigateToList.value = true
        }
    }

    fun onNavigatedToList() {
        navigateToList.value = false
    }

//    fun navigateToList(view: View) {
//        view.findNavController().navigate(R.id.action_editTaskFragment_to_tasksFragment)
//    }
}