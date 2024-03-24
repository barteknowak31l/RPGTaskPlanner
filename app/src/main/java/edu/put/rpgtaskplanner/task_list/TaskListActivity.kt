package edu.put.rpgtaskplanner.task_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import edu.put.rpgtaskplanner.databinding.ActivityTaskListBinding


class TaskListActivity : AppCompatActivity(), TaskListFragment.Listener {

    private lateinit var binding: ActivityTaskListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun itemClicked(id: Int) {
//        TODO("Not yet implemented")

        return
    }
}
