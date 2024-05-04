package edu.put.rpgtaskplanner.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.Timestamp
import edu.put.rpgtaskplanner.model.Task

class TaskRepository(private val firestore: FirebaseFirestore) {

    private val collection = firestore.collection("tasks")

    enum class TaskFields {
        character_id,
        difficulty,
        estimated_end_date,
        exp_reward,
        gold_reward,
        start_date,
        status,
        task_name,
        energy_cost,
        health_cost
    }

    fun getTaskByName(taskName: String, onComplete: (Task?) -> Unit) {
        collection.whereEqualTo(TaskFields.task_name.name, taskName)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]
                    val task = document.toObject(Task::class.java)
                    onComplete(task)
                } else {
                    onComplete(null)
                }
            }
            .addOnFailureListener { exception ->
                onComplete(null)
            }
    }

    fun getTaskByCharacterId(characterId: String, onComplete: (List<Task>) -> Unit) {
        collection.whereEqualTo(TaskFields.character_id.name, characterId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val tasks = mutableListOf<Task>()
                for (document in querySnapshot.documents) {
                    val task = document.toObject(Task::class.java)
                    task?.let { tasks.add(it) }
                }
                onComplete(tasks)
            }
            .addOnFailureListener { exception ->
                onComplete(emptyList())
                Log.e("TaskRepository", "Error getting tasks: $exception")
            }
    }

    fun saveTask(task: Task, onComplete: (Boolean) -> Unit) {
        collection
            .add(task)
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener { exception ->
                onComplete(false)
            }
    }

    fun updateTask(taskName: String, updates: Map<TaskFields, Any>, onComplete: (Boolean) -> Unit) {
        val updatesMap = updates.mapKeys { it.key.name }
        collection.document(taskName)
            .update(updatesMap)
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener { exception ->
                onComplete(false)
            }
    }
}
