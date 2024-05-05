package edu.put.rpgtaskplanner.utility

import edu.put.rpgtaskplanner.model.Task
object TaskManager {
    private var currentTask: Task? = null

    fun setCurrentTask(task: Task) {
        currentTask = task
    }

    fun getCurrentTask(): Task? {
        return currentTask
    }

    fun setToNull() {
        currentTask = null
    }
}