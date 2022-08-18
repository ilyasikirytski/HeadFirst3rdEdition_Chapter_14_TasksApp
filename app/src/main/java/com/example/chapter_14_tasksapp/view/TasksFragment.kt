package com.example.chapter_14_tasksapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chapter_14_tasksapp.R
import com.example.chapter_14_tasksapp.databinding.FragmentTasksBinding
import com.example.chapter_14_tasksapp.model.TaskDatabase
import com.example.chapter_14_tasksapp.view_model.TaskViewModelFactory
import com.example.chapter_14_tasksapp.view_model.TasksViewModel

class TasksFragment : Fragment(R.layout.fragment_tasks) {

//    private var _binding: FragmentTasksBinding? = null
//    private val binding get() = _binding!!

    // TOD в котлине уже давно есть расширение viewModels, можно использовать
    private val factoryProducer = {
        TaskViewModelFactory(
            TaskDatabase.getInstance(requireContext().applicationContext)
        )
    }
    private val viewModel: TasksViewModel by viewModels(factoryProducer = factoryProducer)

    var adapter: TaskItemAdapter? = null

    private val viewBinding by viewBinding(FragmentTasksBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TOD адаптер пересоздается каждый раз при создании вьюшки,
        //  то есть нажал бек / перевернул экран и все, все слетело

        // TOD можно использовать ссылку на метод - val adapter = TaskItemAdapter(viewModel::onTaskClicked)
        adapter = TaskItemAdapter(viewModel::onTaskClicked)
    }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        // TOD для байндинга тоже давно придумали делегаты, уже готовые даже есть чтобы
//        //  самому не писать с помощью kotlin delegated property - com.github.kirich1409:viewbindingpropertydelegate-noreflection
////        _binding = FragmentTasksBinding.inflate(inflater, container, false)
////        val view = viewBinding.root
//
//        // TOD грубо говоря сам попытался сделать без DI все, поэтому все так страшно)))
////        val application = requireNotNull(this.activity).application
////        val dao = TaskDatabase.getInstance(application).taskDao
////        val viewModelFactory = TaskViewModelFactory(dao)
////        val viewModel = ViewModelProvider(this, viewModelFactory).get(TasksViewModel::class.java)
//        return view
//    }

    // TOD вообще всю эту обработку (кроме двух строчек с байндингом) лучше делать в onViewCreated,
    //  когда вьюшка уже создана
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.tasksList.adapter = adapter

        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            adapter?.submitList(it)
        })

        viewBinding.saveButton.setOnClickListener {
            // TOD слишком много логики тут, должен просто вызвать
            //  viewModel.saveButtonClicked(newTaskName), дальше все на liveData/observable
            viewModel.addTask(viewBinding.taskName.text.toString())
            viewBinding.taskName.text = null
        }

        viewModel.navigateToTask.observe(viewLifecycleOwner, Observer {
            val action = TasksFragmentDirections.actionTasksFragmentToEditTaskFragment(it)
            viewBinding.root.findNavController().navigate(action)
        })
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
////        _binding = null
//    }
}