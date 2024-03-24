package edu.put.rpgtaskplanner.task_list

import java.util.Date


enum class Difficulty{
    EASY,MEDIUM,HARD
}
class Task(val name: String,
           val description: String,
           val healthUsed: Int,
           val resourceUsed: Int)
{


    var difficulty: Difficulty? = null
    var startDate: Date? = null
    var minimumEndDate: Date? = null
    var endDate: Date? = null
    var goldReward: Int? = null
    var experienceReward: Int? = null


//     init {
//         calculate difficulty
//         calculate rewards
//         calculate minimum end date
//     }


    companion object{

        private val tasks = mutableListOf<Task>()
        init {
            tasks.add(Task("Task 1", "Description 1", 10, 10))
            tasks.add(Task("Task 2", "Description 2", 20, 20))
            tasks.add(Task("Task 3", "Description 3", 30, 30))
            tasks.add(Task("Task 3", "Description 3", 30, 30))
            tasks.add(Task("Task 3", "Description 3", 30, 30))
            tasks.add(Task("Task 3", "Description 3", 30, 30))
            tasks.add(Task("Task 3", "Description 3", 30, 30))
            tasks.add(Task("Task 3", "Description 3", 30, 30))
            tasks.add(Task("Task 3", "Description 3", 30, 30))
            tasks.add(Task("Task 3", "Description 3", 30, 30))
            tasks.add(Task("Task 3", "Description 3", 30, 30))
            tasks.add(Task("Task 3", "Description 3", 30, 30))
            tasks.add(Task("Task 3", "Description 3", 30, 30))
            tasks.add(Task("Task 3", "Description 3", 30, 30))
            tasks.add(Task("Task 3", "Description 3", 30, 30))
            tasks.add(Task("Task 3", "Description 3", 30, 30))
            tasks.add(Task("Task 3", "Description 3", 30, 30))
            tasks.add(Task("Task 3", "Description 3", 30, 30))
            tasks.add(Task("Task 3", "Description 3", 30, 30))

        }

        fun getTasks(): List<Task>
        {
            return tasks.toList()
        }

    }
}