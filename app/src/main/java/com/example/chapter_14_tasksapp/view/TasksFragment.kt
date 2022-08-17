package com.example.chapter_14_tasksapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.chapter_14_tasksapp.databinding.FragmentTasksBinding
import com.example.chapter_14_tasksapp.model.TaskDatabase
import com.example.chapter_14_tasksapp.view_model.TaskItemAdapter
import com.example.chapter_14_tasksapp.view_model.TaskViewModelFactory
import com.example.chapter_14_tasksapp.view_model.TasksViewModel

class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    // TODO в котлине уже давно есть расширение viewModels, можно использовать
    private val factoryProducer = {
        TaskViewModelFactory(
            TaskDatabase.getInstance(requireContext().applicationContext)
        )
    }
    private val viewModel: TasksViewModel by viewModels(factoryProducer = factoryProducer)

    // TODO вообще всю эту обработку (кроме двух строчек с байндингом) лучше делать в onViewCreated,
    //  когда вьюшка уже создана

    // TODO форматирование шапки метода не по кодстайлу котлина, каждый параметр с новой строки
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // TODO для байндинга тоже давно придумали делегаты, уже готовые даже есть чтобы
        //  самому не писать с помощью kotlin delegated property - com.github.kirich1409:viewbindingpropertydelegate-noreflection
        // Inflate the layout for this fragment
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        val view = binding.root

        // TODO грубо говоря сам попытался сделать без DI все, поэтому все так страшно)))
//        val application = requireNotNull(this.activity).application
//        val dao = TaskDatabase.getInstance(application).taskDao
//        val viewModelFactory = TaskViewModelFactory(dao)
//        val viewModel = ViewModelProvider(this, viewModelFactory).get(TasksViewModel::class.java)

        // TODO адаптер пересоздается каждый раз при создании вьюшки,
        //  то есть нажал бек / перевернул экран и все, все слетело

        // TODO можно использовать ссылку на метод - val adapter = TaskItemAdapter(viewModel::onTaskClicked)
        val adapter = TaskItemAdapter { taskId ->
            if (taskId != null) {
                viewModel.onTaskClicked(taskId)
            }
        }

        binding.tasksList.adapter = adapter

        // TOD тут даже студия кричит - отформатируй код, и зачем тут let))) -
        //  viewModel.tasks.observe(viewLifecycleOwner) { adapter.submitList(it) }
        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        binding.saveButton.setOnClickListener {
            // TODO слишком много логики тут, должен просто вызвать
            //  viewModel.saveButtonClicked(newTaskName), дальше все на liveData/observable
//            viewModel.newTaskName =
//                if (binding.taskName.text.isEmpty()) "Default Name" else binding.taskName.text.toString()
            viewModel.addTask(if (binding.taskName.text.isEmpty()) "Default Name" else binding.taskName.text.toString())
            binding.taskName.text = null
        }

        // TODO опять же, отформатируй код, и зачем тут снова let))) -
        //  viewModel.tasks.observe(viewLifecycleOwner) { adapter.submitList(it) }
        viewModel.navigateToTask.observe(viewLifecycleOwner, Observer { taskId ->
            taskId?.let {
                val action = TasksFragmentDirections.actionTasksFragmentToEditTaskFragment(taskId)
                this.findNavController().navigate(action)
//                viewModel.onTaskNavigated()
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