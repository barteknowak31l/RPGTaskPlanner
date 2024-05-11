package edu.put.rpgtaskplanner.task_list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.repository.TaskRepository
import edu.put.rpgtaskplanner.utility.UserManager
import edu.put.rpgtaskplanner.model.Task
import edu.put.rpgtaskplanner.model.TaskStatus
import edu.put.rpgtaskplanner.utility.TaskManager


class TaskListFragment : ListFragment() {
    interface Listener
    {
        fun itemClicked(id: Int)
    }
    private var listener: Listener? = null

    val db = Firebase.firestore
    val taskRepository = TaskRepository(db)
    var taskList: List<Task> = mutableListOf()
    private var adapter:  ArrayAdapter<String>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is Listener)
        {
            listener = context
        }
        else
        {
            throw RuntimeException("$context must implement Listener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)

        adapter = ArrayAdapter<String>(inflater.context, android.R.layout.simple_list_item_1)
        listAdapter = adapter
        listAdapter = adapter
        refreshAdapter()
        return view
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        listener?.itemClicked(position)
        TaskManager.setCurrentTask(taskList[position])
    }

    override fun onResume() {
        super.onResume()
        refreshAdapter()
    }

    private fun refreshAdapter()
    {
        val user = UserManager.getCurrentUser()
        taskRepository.getTasksByCharacterIdFilterByStatusAsync(user?.character_id!!, TaskStatus.IN_PROGRESS).observe(viewLifecycleOwner) { tasks ->
            val taskNames = tasks.map { it.task_name }
            if(adapter != null)
            {
                adapter!!.clear()
                adapter!!.addAll(taskNames)
            }
            taskList = tasks
        }
    }


}