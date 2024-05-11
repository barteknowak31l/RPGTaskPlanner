package edu.put.rpgtaskplanner.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.Timestamp
import edu.put.rpgtaskplanner.model.Task
import edu.put.rpgtaskplanner.model.TaskStatus

class TaskRepository(private val firestore: FirebaseFirestore) {

    private val collection = firestore.collection("tasks")

    private val tasksLiveData = MutableLiveData<List<Task>>()

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

    fun getTasksByCharacterIdFilterByStatusAsync(characterId: String, status: TaskStatus): LiveData<List<Task>> {
        val tasksLiveData = MutableLiveData<List<Task>>()

        // Wywołaj zapytanie asynchroniczne
        collection.whereEqualTo(TaskFields.character_id.name, characterId)
            .whereEqualTo(TaskFields.status.name, status.id)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val tasks = mutableListOf<Task>()
                for (document in querySnapshot.documents) {
                    val task = document.toObject(Task::class.java)
                    task?.let { tasks.add(it) }
                }
                tasksLiveData.value = tasks // Zaktualizuj wartość LiveData
            }
            .addOnFailureListener { exception ->
            }

        return tasksLiveData
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
    fun updateTaskByCharacterId(taskName: String, characterId: String, updates: Map<TaskFields, Any>, onComplete: (Boolean) -> Unit) {
        val updatesMap = updates.mapKeys { it.key.name }

        // Dodaj warunek dotyczący pola character_id
        collection.whereEqualTo(TaskFields.task_name.name, taskName)
            .whereEqualTo(TaskFields.character_id.name, characterId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    // Jeśli istnieje zadanie o danej nazwie i character_id, wykonaj aktualizację
                    collection.document(querySnapshot.documents[0].id)
                        .update(updatesMap)
                        .addOnSuccessListener {
                            onComplete(true)
                        }
                        .addOnFailureListener { exception ->
                            onComplete(false)
                        }
                } else {
                    // Jeśli nie ma zadania o danej nazwie i character_id, zakończ z informacją o niepowodzeniu
                    onComplete(false)
                }
            }
            .addOnFailureListener { exception ->
                onComplete(false)
            }
    }


    fun deleteTaskByCharacterId(taskName: String, characterId: String, onComplete: (Boolean) -> Unit) {
        // Znajdź zadanie na podstawie nazwy i ID postaci
        collection.whereEqualTo(TaskFields.task_name.name, taskName)
            .whereEqualTo(TaskFields.character_id.name, characterId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    // Jeśli istnieje zadanie o podanej nazwie i ID postaci, usuń je
                    val documentId = querySnapshot.documents[0].id
                    collection.document(documentId)
                        .delete()
                        .addOnSuccessListener {
                            onComplete(true)
                        }
                        .addOnFailureListener { exception ->
                            onComplete(false)
                        }
                } else {
                    // Jeśli nie znaleziono zadania o podanej nazwie i ID postaci, zwróć informację o niepowodzeniu
                    onComplete(false)
                }
            }
            .addOnFailureListener { exception ->
                onComplete(false)
            }
    }

}
