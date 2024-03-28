package edu.put.rpgtaskplanner.task_list

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.put.rpgtaskplanner.databinding.ActivityTaskListBinding


class TaskListActivity : AppCompatActivity(), TaskListFragment.Listener {

    private lateinit var binding: ActivityTaskListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCreate.setOnClickListener{
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }

    }

    override fun itemClicked(id: Int) {
        val intent = Intent(this, TaskDetailActivity::class.java)
        startActivity(intent)
    }
}
