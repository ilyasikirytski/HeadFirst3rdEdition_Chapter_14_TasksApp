package com.example.chapter_14_tasksapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chapter_14_tasksapp.R
import com.example.chapter_14_tasksapp.databinding.FragmentTasksBinding
import com.example.chapter_14_tasksapp.view_model.TaskViewModelFactory
import com.example.chapter_14_tasksapp.view_model.TasksViewModel

class TasksFragment : Fragment(R.layout.fragment_tasks) {

    private val factoryProducer = {
        TaskViewModelFactory(requireContext())
    }
    private val viewModel: TasksViewModel by viewModels(factoryProducer = factoryProducer)

    private var adapter: TaskItemAdapter? = null

    private val viewBinding by viewBinding(FragmentTasksBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TaskItemAdapter(viewModel::onTaskClicked)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.load()

        viewBinding.tasksList.adapter = adapter

        viewBinding.saveButton.setOnClickListener {
            viewModel.addTask(viewBinding.taskName.text.toString())
            viewBinding.taskName.text = null
        }

        viewModel.tasks?.observe(viewLifecycleOwner, Observer {
            adapter?.submitList(it)
        })

        viewModel.navigateToTask.observe(viewLifecycleOwner, Observer {
            val action = TasksFragmentDirections.actionTasksFragmentToEditTaskFragment(it)
            viewBinding.root.findNavController().navigate(action)
        })
    }
}