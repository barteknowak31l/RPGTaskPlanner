package edu.put.rpgtaskplanner.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import edu.put.rpgtaskplanner.model.CharacterClass
import edu.put.rpgtaskplanner.model.Item
import edu.put.rpgtaskplanner.model.ItemType

class ItemRepository(private val firestore: FirebaseFirestore) {

    private val collection = firestore.collection("equipments")

    enum class ItemFields {
        bonus,
        description,
        image_resource_id,
        level,
        price,
        type,
        item_name
    }

    fun getItemsByCharacterId(characterId: String, onComplete: (List<Item>) -> Unit) {
        collection.document("${characterId}_eq")
            .collection("items")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val items = mutableListOf<Item>()
                for (document in querySnapshot.documents) {
                    val item = document.toObject(Item::class.java)
                    item?.let { items.add(it) }
                }
                onComplete(items)
            }
            .addOnFailureListener { exception ->
                onComplete(emptyList())
            }
    }

    fun updateItem(characterId: String, itemName: String, updates: Map<ItemFields, Any>, onComplete: (Boolean) -> Unit) {
        val updatesMap = updates.mapKeys { it.key.name }
        val itemDocument = collection.document("${characterId}_eq")
            .collection("items")
            .document(itemName)

        itemDocument.update(updatesMap)
            .addOnSuccessListener {
                val itemInUseDocument = collection.document("${characterId}_eq")
                    .collection("items_in_use")
                    .document(itemName)

                itemInUseDocument.update(updatesMap)
                    .addOnSuccessListener {
                        onComplete(true)
                    }
                    .addOnFailureListener { exception ->
                        onComplete(false)
                    }
            }
            .addOnFailureListener { exception ->
                onComplete(false)
            }
    }

    fun getItemTemplatesByTypeAndClass(type: ItemType, characterClass: CharacterClass): LiveData<List<Item>> {
        val templatesLiveData = MutableLiveData<List<Item>>()
        val correctedType = if (!type.name.lowercase().endsWith("s")) {
            "${type.name}s"
        }
        else
        {
            type.name
        }
        val typePath = correctedType.lowercase()
        val typePathWithoutS = type.name.lowercase()

        val collectionPath = "item_templates/$typePath/${typePathWithoutS}_templates"

        firestore.collection(collectionPath)
            .whereEqualTo("class", characterClass.id)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val templates = mutableListOf<Item>()
                for (document in querySnapshot.documents) {
                    val template = document.toObject(Item::class.java)
                    template?.let { templates.add(it) }
                }
                templatesLiveData.value = templates
                Log.d("ItemRepository","query ok " + collectionPath)

            }
            .addOnFailureListener { exception ->
                Log.d("ItemRepository", exception.toString())
            }

        return templatesLiveData
    }

}
