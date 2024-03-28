package edu.put.rpgtaskplanner.task_list

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import edu.put.rpgtaskplanner.databinding.ActivityAddTaskBinding

class AddTaskActivity : FragmentActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}

