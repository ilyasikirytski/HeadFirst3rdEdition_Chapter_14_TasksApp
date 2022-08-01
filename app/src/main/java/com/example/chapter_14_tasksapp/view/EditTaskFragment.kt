package com.example.chapter_14_tasksapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.chapter_14_tasksapp.R
import com.example.chapter_14_tasksapp.databinding.FragmentEditTaskBinding
import com.example.chapter_14_tasksapp.model.TaskDatabase
import com.example.chapter_14_tasksapp.view_model.EditTaskViewModel
import com.example.chapter_14_tasksapp.view_model.EditTaskViewModelFactory

// TODO те же проблемы что и у TasksFragment
class EditTaskFragment : Fragment() {
    private var _binding: FragmentEditTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditTaskBinding.inflate(inflater, container, false)
        val view = binding.root
        val taskId = EditTaskFragmentArgs.fromBundle(requireArguments()).taskId

        val application = requireNotNull(this.activity).application
        val dao = TaskDatabase.getInstance(application).taskDao

        val viewModelFactory = EditTaskViewModelFactory(taskId, dao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(EditTaskViewModel::class.java)

        binding.updateButton.setOnClickListener {
            viewModel.task.value?.taskName = binding.taskName.text.toString()
            viewModel.task.value?.taskDone = binding.taskDone.isChecked
            viewModel.updateTask()
        }
        binding.deleteButton.setOnClickListener {
            viewModel.deleteTask()
        }

        viewModel.task.observe(viewLifecycleOwner, Observer {
            binding.taskName.setText(viewModel.task.value?.taskName)
            it?.let { binding.taskDone.isChecked = it.taskDone }
        })

        viewModel.navigateToList.observe(viewLifecycleOwner, Observer { navigate ->
            // TODO никаких if во вьюшке, ей нужно просто сказать что сделать,
            //  все ифы и проверки во вьюмодели
            if (navigate) {
                view.findNavController().navigate(R.id.action_editTaskFragment_to_tasksFragment)
                viewModel.onNavigatedToList()
            }
        })
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}