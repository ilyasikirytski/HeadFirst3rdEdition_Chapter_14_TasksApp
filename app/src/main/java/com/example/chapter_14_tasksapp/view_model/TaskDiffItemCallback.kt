package com.example.chapter_14_tasksapp.view_model
// TODO DiffUtil.ItemCallback это же часть recycler view, то есть это view слой, такое обычно
//  ложат рядом с вьюшкой/адаптером (переместить в другой пакет)

import androidx.recyclerview.widget.DiffUtil
import com.example.chapter_14_tasksapp.data.TaskEntity

class TaskDiffItemCallback : DiffUtil.ItemCallback<TaskEntity>() {
    override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
        return oldItem.taskId == newItem.taskId
    }

    override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
        return oldItem == newItem
    }
}