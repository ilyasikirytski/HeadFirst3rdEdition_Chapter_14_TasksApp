package com.example.chapter_14_tasksapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.chapter_14_tasksapp.databinding.FragmentTasksBinding
import com.example.chapter_14_tasksapp.model.TaskDatabase
import com.example.chapter_14_tasksapp.view_model.TaskItemAdapter
import com.example.chapter_14_tasksapp.view_model.TaskViewModelFactory
import com.example.chapter_14_tasksapp.view_model.TasksViewModel

class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = TaskDatabase.getInstance(application).taskDao
        val viewModelFactory = TaskViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(TasksViewModel::class.java)

        val adapter = TaskItemAdapter { taskId ->
            viewModel.onTaskClicked(taskId)
        }

        binding.tasksList.adapter = adapter
        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            it.let { adapter.submitList(it) }
        })

        binding.saveButton.setOnClickListener {
            viewModel.newTaskName = binding.taskName.text.toString()
            binding.taskName.text = null
            viewModel.addTask()
        }

        viewModel.navigateToTask.observe(viewLifecycleOwner, Observer { taskId ->
            taskId?.let {
                val action = TasksFragmentDirections.actionTasksFragmentToEditTaskFragment(taskId)
                this.findNavController().navigate(action)
                viewModel.onTaskNavigated()
            }
        }
        )
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}