package com.example.chapter_14_tasksapp.view_model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chapter_14_tasksapp.TaskDiffItemCallback
import com.example.chapter_14_tasksapp.databinding.TaskItemBinding
import com.example.chapter_14_tasksapp.model.Task

// TODO это вьюшка же, адаптер это часть отображения, поэтому лежит всегда в пакете view
class TaskItemAdapter(
    // TODO приватное
    val clickListener: (taskId: Long) -> Unit
) : ListAdapter<Task, TaskItemAdapter.TaskItemViewHolder>(TaskDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder =
        TaskItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }


    class TaskItemViewHolder(
        var binding: TaskItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        // TODO у котлина по кодстайлу companion должен быть в конце, но тут часто разные команды
        //  договариваются по разному (https://kotlinlang.org/docs/coding-conventions.html#class-layout)
        companion object {
            fun inflateFrom(parent: ViewGroup): TaskItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TaskItemBinding.inflate(layoutInflater, parent, false)
                return TaskItemViewHolder(binding)
            }
        }

        // TODO дублирование `(taskId: Long) -> Unit`, лучше использовать typealias
        fun bind(item: Task, clickListener: (taskId: Long) -> Unit) {
            binding.taskName.text = item.taskName
            binding.taskDone.isChecked = item.taskDone
            binding.root.setOnClickListener {
                clickListener(item.taskId)
            }
        }
    }
}
