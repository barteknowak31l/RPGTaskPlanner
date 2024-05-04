package edu.put.rpgtaskplanner.model

import java.sql.Date
import java.sql.Timestamp

enum class TaskDifficulty(val id: Int) {
    DIFFICULTY_EASY(0),
    DIFFICULTY_MEDIUM(1),
    DIFFICULTY_HARD(2)
}

enum class TaskStatus(val id: Int) {
    IN_PROGRESS(0),
    DONE(1),
}

class Task {

    var character_id: String = ""
    var difficulty: Number = 0
    lateinit var estimated_end_date: Timestamp
    var exp_reward: Number = 0
    var gold_reward: Number = 0
    var name: String = ""
    lateinit var start_date: Timestamp
    var status: Number = 0

}