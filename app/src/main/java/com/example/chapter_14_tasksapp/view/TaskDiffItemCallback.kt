package com.example.chapter_14_tasksapp.view

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