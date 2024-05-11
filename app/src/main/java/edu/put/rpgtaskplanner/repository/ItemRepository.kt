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
        base_bonus,
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
            }
            .addOnFailureListener { exception ->
            }

        return templatesLiveData
    }


    fun saveItemToCharacterEquipment(characterId: String, item: Item, onComplete: (Boolean) -> Unit) {
        val itemDocumentPath = "equipments/${characterId}_eq/items"

        val characterDocument = firestore.collection("equipments").document("${characterId}_eq")
        characterDocument.get()
            .addOnSuccessListener { snapshot ->
                val existingCharacterId = snapshot.getString("character_id")
                if (existingCharacterId != null && existingCharacterId != characterId) {
                    onComplete(false)
                } else {
                    characterDocument.set(mapOf("character_id" to characterId), SetOptions.merge())

                    val itemsCollection = firestore.collection(itemDocumentPath)
                    val newItemDocument = itemsCollection.document(item.item_name)
                    newItemDocument.set(item)
                        .addOnSuccessListener {
                            onComplete(true)
                        }
                        .addOnFailureListener { exception ->
                            onComplete(false)
                        }
                }
            }
            .addOnFailureListener { exception ->
                onComplete(false)
            }
    }



    fun saveItemToCharacterEquipmentInUse(characterId: String, item: Item, onComplete: (Boolean) -> Unit) {
        val itemDocumentPath = "equipments/${characterId}_eq/items_in_use"

        val characterDocument = firestore.collection("equipments").document("${characterId}_eq")
        characterDocument.get()
            .addOnSuccessListener { snapshot ->
                val existingCharacterId = snapshot.getString("character_id")
                if (existingCharacterId != null && existingCharacterId != characterId) {
                    onComplete(false)
                } else {
                    characterDocument.set(mapOf("character_id" to characterId), SetOptions.merge())

                    val itemsCollection = firestore.collection(itemDocumentPath)
                    val newItemDocument = itemsCollection.document(item.item_name)
                    newItemDocument.set(item)
                        .addOnSuccessListener {
                            onComplete(true)
                        }
                        .addOnFailureListener { exception ->
                            onComplete(false)
                        }
                }
            }
            .addOnFailureListener { exception ->
                onComplete(false)
            }
    }

    fun deleteItemFromCharacterEquipment(characterId: String, itemName: String, onComplete: (Boolean) -> Unit) {
        val itemDocumentPath = "equipments/${characterId}_eq/items"

        val itemDocument = firestore.collection(itemDocumentPath).document(itemName)
        itemDocument.delete()
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener { exception ->
                onComplete(false)
            }
    }



    fun deleteItemFromCharacterEquipmentInUse(characterId: String, itemName: String, onComplete: (Boolean) -> Unit) {
        val itemDocumentPath = "equipments/${characterId}_eq/items_in_use"

        val itemDocument = firestore.collection(itemDocumentPath).document(itemName)
        itemDocument.delete()
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener { exception ->
                onComplete(false)
            }
    }

    fun getItemsFromCharacterEquipment(characterId: String, onComplete: (List<Item>) -> Unit) {
        val itemCollection = firestore.collection("equipments/${characterId}_eq/items")
        itemCollection
            .get()
            .addOnSuccessListener{ querySnapshot ->
                val itemList = mutableListOf<Item>()
                for (document in querySnapshot.documents) {
                    val item = document.toObject(Item::class.java)
                    item?.let {itemList.add(it)}
                }
                onComplete(itemList)
            }
            .addOnFailureListener{ exception ->
                onComplete(emptyList())
            }
    }

    fun getItemsFromCharacterEquipmentInUse(characterId: String, onComplete: (List<Item>) -> Unit) {
        val itemCollection = firestore.collection("equipments/${characterId}_eq/items_in_use")
        itemCollection
            .get()
            .addOnSuccessListener{ querySnapshot ->
                val itemList = mutableListOf<Item>()
                for (document in querySnapshot.documents) {
                    val item = document.toObject(Item::class.java)
                    item?.let {itemList.add(it)}
                }
                onComplete(itemList)
            }
            .addOnFailureListener{ exception ->
                onComplete(emptyList())
            }
    }

    fun getItemFromCharacterEquipmentInUseFilterByType(characterId: String, itemType: Int, onComplete: (Item?) -> Unit) {
        val itemCollection = firestore.collection("equipments/${characterId}_eq/items_in_use")
        itemCollection
            .whereEqualTo(ItemFields.type.toString(),itemType)
            .get()
            .addOnSuccessListener{ querySnapshot ->
                onComplete(querySnapshot.documents.firstOrNull()?.toObject(Item::class.java))
            }
            .addOnFailureListener{ exception ->
                onComplete(null)
            }
    }


}
