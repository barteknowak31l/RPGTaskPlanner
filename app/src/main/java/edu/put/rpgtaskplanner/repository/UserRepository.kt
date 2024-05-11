package edu.put.rpgtaskplanner.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import edu.put.rpgtaskplanner.model.User

class UserRepository(private val firestore: FirebaseFirestore) {

    private val collection = firestore.collection("users")

    enum class UserFields {
        email,
        character_id,
        easy_task_done,
        medium_task_done,
        hard_task_done,
        next_refresh_date
    }

    fun getUserByEmail(userEmail: String, onComplete: (User?) -> Unit) {
        collection.whereEqualTo(UserFields.email.name, userEmail)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]
                    val user = document.toObject(User::class.java)
                    onComplete(user)
                } else {
                    onComplete(null)
                }
            }
            .addOnFailureListener { exception ->
                onComplete(null)
            }
    }

    fun saveUser(user: User, onComplete: (Boolean) -> Unit) {
        collection.document(user.email)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener { exception ->
                onComplete(false)
            }
    }

    fun updateUser(userEmail: String, updates: Map<UserFields, Any>, onComplete: (Boolean) -> Unit) {
        val updatesMap = updates.mapKeys { it.key.name }
        collection.document(userEmail)
            .update(updatesMap)
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener { exception ->
                onComplete(false)
            }
    }
}
