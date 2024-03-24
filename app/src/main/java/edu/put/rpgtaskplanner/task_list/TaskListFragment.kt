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
import edu.put.rpgtaskplanner.R

class TaskListFragment : ListFragment() {

    interface Listener
    {
        fun itemClicked(id: Int)
    }
    private var listener: Listener? = null

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

        val taskList = Task.getTasks()
        val taskNames = taskList.map {it.name}
        val adapter = ArrayAdapter(inflater.context, android.R.layout.simple_list_item_1, taskNames)
        listAdapter = adapter

        return view
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        listener?.itemClicked(position)
    }
}