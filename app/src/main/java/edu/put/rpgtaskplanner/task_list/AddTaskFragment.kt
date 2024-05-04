package edu.put.rpgtaskplanner.task_list

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.compose.ui.text.toLowerCase
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.model.TaskDifficulty
import edu.put.rpgtaskplanner.utility.CharacterManager
import edu.put.rpgtaskplanner.utility.TaskCreator
import java.text.SimpleDateFormat
import java.util.Locale

class AddTaskFragment : Fragment() {

    val taskCreator = TaskCreator()
    private lateinit var healthEditText: EditText
    private lateinit var energyEditText: EditText
    private lateinit var nameEditText: EditText

    private lateinit var difficultyTextView: TextView
    private lateinit var goldTextView: TextView
    private lateinit var expTextView: TextView
    private lateinit var estimatedEndDate: TextView

    private lateinit var currentHpTextView: TextView
    private lateinit var currentEnergyTextView: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_add_task, container, false)

        healthEditText = rootView.findViewById(R.id.editTextHp)
        energyEditText = rootView.findViewById(R.id.editTextResource)
        nameEditText = rootView.findViewById(R.id.editTextCharacterName)
        difficultyTextView = rootView.findViewById(R.id.textViewDifficulty)
        goldTextView = rootView.findViewById(R.id.textViewGold)
        expTextView = rootView.findViewById(R.id.textViewExperience)
        estimatedEndDate = rootView.findViewById(R.id.textViewMinEndDate)
        currentHpTextView = rootView.findViewById(R.id.textViewCurrentHp)
        currentEnergyTextView = rootView.findViewById(R.id.textViewCurrentResource)


        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                val health = healthEditText.text.toString().toDoubleOrNull() ?: 0.0
                val energy = energyEditText.text.toString().toDoubleOrNull() ?: 0.0
                taskCreator.updateTask(nameEditText.text.toString(), health, energy)
                updateDisplay()

            }
        }

        healthEditText.addTextChangedListener(textWatcher)
        energyEditText.addTextChangedListener(textWatcher)
        nameEditText.addTextChangedListener(textWatcher)


        val health = healthEditText.text.toString().toDoubleOrNull() ?: 0.0
        val energy = energyEditText.text.toString().toDoubleOrNull() ?: 0.0
        taskCreator.createTask(nameEditText.text.toString(), health, energy)
        difficultyTextView.text = getString(R.string.add_task_difficulty, TaskDifficulty.DIFFICULTY_EASY.toString().lowercase())
        updateDisplay()

        val addButton = rootView.findViewById<Button>(R.id.buttonCreate)
        addButton.setOnClickListener {


            taskCreator.saveTask { success, message ->
                if (success) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    val intent = Intent(context,TaskListActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }

        }


        return rootView
    }

    fun updateDisplay()
    {
        val task = taskCreator.task

        val character = CharacterManager.getCurrentCharacter()

        if(character != null)
        {
            currentHpTextView.text = character.current_health.toString()
            currentEnergyTextView.text = character.current_energy.toString()
        }

        if(healthEditText.text.toString() == "" && energyEditText.text.toString() == "")
        {
            difficultyTextView.text = getString(R.string.add_task_difficulty, getString(R.string.task_difficulty_easy))
            goldTextView.text = getString(R.string.add_task_gold,"50.0")
            expTextView.text = getString(R.string.add_task_experience,"50.0")
            estimatedEndDate.text = getString(R.string.add_task_min_end_date, "-")
        }
        else
        {
            var difficultyText:String = ""
            when (task.difficulty)
            {
                0 -> difficultyText = getString(R.string.task_difficulty_easy)
                1 -> difficultyText = getString(R.string.task_difficulty_medium)
                2 -> difficultyText = getString(R.string.task_difficulty_hard)
            }

            difficultyTextView.text = getString(R.string.add_task_difficulty, difficultyText)
            goldTextView.text = getString(R.string.add_task_gold,task.gold_reward.toString())
            expTextView.text = getString(R.string.add_task_experience,task.exp_reward.toString())
            val dateFormat = SimpleDateFormat("dd.MM.yy HH:mm", Locale.getDefault())
            val formattedDate = dateFormat.format(task.estimated_end_date)
            estimatedEndDate.text = getString(R.string.add_task_min_end_date, formattedDate)
        }
    }

}