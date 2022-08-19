package com.example.chapter_14_tasksapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chapter_14_tasksapp.R
import com.example.chapter_14_tasksapp.data.TaskEntity
import com.example.chapter_14_tasksapp.databinding.FragmentEditTaskBinding
import com.example.chapter_14_tasksapp.view_model.EditTaskViewModel
import com.example.chapter_14_tasksapp.view_model.EditTaskViewModelFactory

class EditTaskFragment : Fragment(R.layout.fragment_edit_task) {

    private val factoryProducer = {
        EditTaskViewModelFactory(requireContext())
    }
    private val viewModel: EditTaskViewModel by viewModels(factoryProducer = factoryProducer)
    private val viewBinding by viewBinding(FragmentEditTaskBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val taskId = EditTaskFragmentArgs.fromBundle(requireArguments()).taskId
        viewModel.load(taskId)

        viewBinding.updateButton.setOnClickListener {
            viewModel.updateTask(
                TaskEntity(
                    viewBinding.taskName.text.toString(),
                    taskId,
                    viewBinding.taskDone.isChecked
                )
            )
        }
        viewBinding.deleteButton.setOnClickListener {
            viewModel.deleteTask()
        }

        viewModel.task?.observe(viewLifecycleOwner, Observer {
            viewBinding.taskName.setText(viewModel.task!!.value?.taskName)
            it?.let { viewBinding.taskDone.isChecked = it.taskDone }
        })

        viewModel.navigateToList.observe(viewLifecycleOwner, Observer {
            viewBinding.root.findNavController()
                .navigate(R.id.action_editTaskFragment_to_tasksFragment)
        })
    }
}