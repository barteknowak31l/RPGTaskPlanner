package edu.put.rpgtaskplanner.utility

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.put.rpgtaskplanner.model.Task
import edu.put.rpgtaskplanner.model.TaskDifficulty
import edu.put.rpgtaskplanner.model.TaskStatus
import edu.put.rpgtaskplanner.repository.CharacterRepository
import edu.put.rpgtaskplanner.repository.TaskRepository
import java.util.Date
import java.util.Calendar

class TaskCreator {

    val db = Firebase.firestore
    val characterRepository = CharacterRepository(db)
    val taskRepository = TaskRepository(db)

    val task: Task = Task()
    val user = UserManager.getCurrentUser()
    val character = CharacterManager.getCurrentCharacter()


    fun createTask(name: String, health: Double, energy:Double)
    {
        if (user != null) {
            task.character_id = user.character_id
        }
    }

    fun updateTask(name: String, health: Double, energy:Double)
    {

        task.task_name = name
        task.status = TaskStatus.IN_PROGRESS.id
        task.health_cost = health
        task.energy_cost = energy
        task.start_date = Date()

        // policz progi trudnosci
        var easyThreshold = 0.0
        var mediumThreshold = 0.0
        var resourceMaxSum = 0.0
        var resourceSum = 0.0
        if(character != null)
        {
            resourceMaxSum = character.max_energy + character.max_health
            resourceSum = health + energy
            easyThreshold = resourceMaxSum * 0.33
            mediumThreshold = resourceMaxSum * 0.66
        }

        // ustal trudnosc zadania:
        var difficulty = TaskDifficulty.DIFFICULTY_EASY.id
        if (resourceSum > easyThreshold && resourceSum < mediumThreshold)
        {
            difficulty = TaskDifficulty.DIFFICULTY_MEDIUM.id
        }
        else if (resourceSum >= mediumThreshold)
        {
            difficulty = TaskDifficulty.DIFFICULTY_HARD.id
        }
        task.difficulty = difficulty

        // policz nagrody (uwzglednij mnozniki i trudnosc)
        var goldMultiplier = character?.gold_multiplier!!
        var goldReward = (50 + health) * (difficulty + 1) * goldMultiplier
        goldReward = goldReward.toInt().toDouble()
        task.gold_reward = goldReward

        var expMultiplier = character?.exp_multiplier!!
        var expReward = (50 + energy) * (difficulty + 1) * expMultiplier
        expReward = expReward.toInt().toDouble()
        task.exp_reward = expReward

        // policz szacowany czas ukonczenia
        val calendar = Calendar.getInstance().apply {
            timeInMillis = task.start_date.time
        }
        val cooldownReduction = 1.0 - character.cooldown_reduction
        when(difficulty)
        {
            0 -> {
                calendar.add(Calendar.MINUTE, (240.0 * cooldownReduction).toInt())
            }
            1 -> {
                calendar.add(Calendar.MINUTE, (240.0 * cooldownReduction).toInt())

            }
            2 -> {
                calendar.add(Calendar.MINUTE, (720.0 * cooldownReduction).toInt())
            }
        }
        var estimatedEndDate = Date(calendar.timeInMillis)
        task.estimated_end_date = estimatedEndDate
    }

    fun saveTask(onComplete: (Boolean, String) -> Unit) {
        taskRepository.getTaskByCharacterId(user?.character_id!!) { tasks ->
            if(!tasks.stream().anyMatch { t -> t.task_name == task.task_name }) {
                save { saveSuccess, message ->
                    onComplete(saveSuccess, message)
                }
            } else {
                onComplete(false, "Zadanie o takiej nazwie już istnieje")
            }
        }
    }

    private fun save(onComplete: (Boolean, String) -> Unit) {
        if(character != null) {
            if(task.task_name == "")
            {
                onComplete(false, "Nazwa zadania nie może być pusta")
                return
            }

            if(task.energy_cost == 0.0 || task.health_cost == 0.0) {
                onComplete(false, "Zasoby nie mogą być równe 0")
                return
            }

            if(character.current_energy < task.energy_cost || character.current_health < task.health_cost) {
                onComplete(false, "Nie masz wystarczającej liczby zasobów")
                return
            }

        }

        if(character != null)
        {
            character.current_health -= task.health_cost
            character.current_energy -= task.energy_cost
            character.current_health = Math.round(character.current_health * 100.0) / 100.0
            character.current_energy = Math.round(character.current_energy * 100.0) / 100.0
        }

        var characterSaveSuccess = false
        val characterUpdatesMap = mapOf(
            CharacterRepository.CharacterFields.current_health to character?.current_health!!,
            CharacterRepository.CharacterFields.current_energy to character.current_energy
        )
        characterRepository.updateCharacter(user?.character_id!!, characterUpdatesMap) { success ->
            characterSaveSuccess = success
            CharacterManager.setCurrentCharacter(character)

            if(success) {
                taskRepository.saveTask(task) { taskSaveSuccess ->
                    onComplete(taskSaveSuccess && characterSaveSuccess, "Zadanie zostało zapisane")
                }
            } else {
                onComplete(false, "Nie udało się zapisać zadania")
            }
        }
    }
}
