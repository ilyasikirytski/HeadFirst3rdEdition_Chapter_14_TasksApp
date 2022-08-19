package com.example.chapter_14_tasksapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chapter_14_tasksapp.data.TaskEntity
import com.example.chapter_14_tasksapp.databinding.TaskItemBinding

typealias OnTaskClicked = (taskId: Long) -> Unit

class TaskItemAdapter(private val clickListener: OnTaskClicked) :
    ListAdapter<TaskEntity, TaskItemAdapter.TaskItemViewHolder>(TaskDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder =
        TaskItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class TaskItemViewHolder(
        private var binding: TaskItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TaskEntity, clickListener: OnTaskClicked) {
            binding.taskName.text = item.taskName
            binding.taskDone.isChecked = item.taskDone
            binding.root.setOnClickListener {
                clickListener(item.taskId)
            }
        }

        // TOD у котлина по кодстайлу companion должен быть в конце, но тут часто разные команды
        //  договариваются по разному (https://kotlinlang.org/docs/coding-conventions.html#class-layout)
        companion object {
            fun inflateFrom(parent: ViewGroup): TaskItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TaskItemBinding.inflate(layoutInflater, parent, false)
                return TaskItemViewHolder(binding)
            }
        }
    }
}
