package com.example.chapter_14_tasksapp
// TODO DiffUtil.ItemCallback это же часть recycler view, то есть это view слой, такое обычно
//  ложат рядом с вьюшкой/адаптером (переместить в другой пакет)

import androidx.recyclerview.widget.DiffUtil
import com.example.chapter_14_tasksapp.model.Task

class TaskDiffItemCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.taskId == newItem.taskId
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}