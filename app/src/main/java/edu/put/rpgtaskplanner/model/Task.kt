package edu.put.rpgtaskplanner.model

import java.util.Date

enum class TaskDifficulty(val id: Int) {
    DIFFICULTY_EASY(0),
    DIFFICULTY_MEDIUM(1),
    DIFFICULTY_HARD(2);
    companion object {
        fun getNameById(id: Int): String {
            return entries.firstOrNull { it.id == id }?.name ?: ""
        }
    }
}

enum class TaskStatus(val id: Int) {
    IN_PROGRESS(0),
    DONE(1),
}

class Task {

    var character_id: String = ""
    var difficulty: Int = 0
    lateinit var estimated_end_date: Date
    var exp_reward: Double = 0.0
    var gold_reward: Double = 0.0
    var task_name: String = ""
    lateinit var start_date: Date
    var status: Int = 0
    var health_cost: Double = 0.0
    var energy_cost: Double = 0.0
}