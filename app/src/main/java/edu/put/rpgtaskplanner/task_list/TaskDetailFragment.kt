package edu.put.rpgtaskplanner.task_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.utility.TaskManager
import java.text.SimpleDateFormat
import java.util.Locale

class TaskDetailFragment : Fragment() {

    private lateinit var taskNameTextView: TextView
    private lateinit var goldRewardTextView: TextView
    private lateinit var expRewardTextView: TextView
    private lateinit var startDateTextView: TextView
    private lateinit var endDateTextView: TextView
    private lateinit var difficultyTextView: TextView
    private lateinit var statusTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_task_detail, container, false)

        taskNameTextView = rootView.findViewById(R.id.textViewTaskName)
        goldRewardTextView = rootView.findViewById(R.id.textViewGoldReward)
        expRewardTextView = rootView.findViewById(R.id.textViewExpReward)
        startDateTextView = rootView.findViewById(R.id.textViewStartDate)
        endDateTextView = rootView.findViewById(R.id.textViewEndDate)
        difficultyTextView = rootView.findViewById(R.id.textViewDifficulty)
        statusTextView = rootView.findViewById(R.id.textViewStatus)

        val task = TaskManager.getCurrentTask()
        if (task != null)
        {
            val dateFormat = SimpleDateFormat("dd.MM.yy HH:mm", Locale.getDefault())
            var difficultyText = ""
            when(task.difficulty)
            {
                0->{
                    difficultyText = getString(R.string.task_difficulty_easy)
                }
                1->{
                    difficultyText = getString(R.string.task_difficulty_medium)
                }
                2->{
                    difficultyText = getString(R.string.task_difficulty_hard)
                }
            }
            var statusText = ""
            when(task.status)
            {
                0->{
                    statusText = getString(R.string.task_status_in_progress)
                }
                1->{
                    statusText = getString(R.string.task_status_done)
                }
            }


            taskNameTextView.text = task.task_name
            goldRewardTextView.text = task.gold_reward.toString()
            expRewardTextView.text = task.exp_reward.toString()
            var formattedDate = dateFormat.format(task.start_date)
            startDateTextView.text =  getString(R.string.task_detail_date_from,formattedDate)
            formattedDate = dateFormat.format(task.estimated_end_date)
            endDateTextView.text = getString(R.string.task_detail_date_to, formattedDate)
            difficultyTextView.text = difficultyText
            statusTextView.text = statusText
        }

        return rootView
    }
}