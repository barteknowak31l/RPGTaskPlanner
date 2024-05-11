package edu.put.rpgtaskplanner.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import edu.put.rpgtaskplanner.model.Character
class CharacterRepository(private val firestore: FirebaseFirestore) {

    private val collection = firestore.collection("characters")

    enum class CharacterFields {
        character_class,
        cooldown_reduction,
        current_energy,
        current_experience,
        current_health,
        energy_regen,
        exp_multiplier,
        gold_multiplier,
        health_regen,
        level,
        max_energy,
        max_health,
        character_name,
        last_resource_refresh_date,
        resource_refresh_cooldown_minutes,
        current_gold
    }

    fun getCharacter(characterId: String, onComplete: (Character?) -> Unit) {
        collection.document(characterId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val character = document.toObject(Character::class.java)
                    onComplete(character)
                } else {
                    onComplete(null)
                }
            }
            .addOnFailureListener { exception ->
                onComplete(null)
            }
    }

    fun saveCharacter(character: Character, onComplete: (Boolean, String?) -> Unit) {
        collection.add(character)
            .addOnSuccessListener { documentReference ->
                val characterId = documentReference.id
                onComplete(true, characterId)
            }
            .addOnFailureListener { exception ->
                onComplete(false, null)
            }
    }



    fun updateCharacter(characterId: String, updates: Map<CharacterFields, Any>, onComplete: (Boolean) -> Unit) {
        val updatesMap = updates.mapKeys { it.key.name }
        collection.document(characterId)
            .update(updatesMap)
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener { exception ->
                onComplete(false)
            }
    }
}
