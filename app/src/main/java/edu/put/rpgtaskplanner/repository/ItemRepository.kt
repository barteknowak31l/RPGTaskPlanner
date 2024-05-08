package edu.put.rpgtaskplanner.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.toObject
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
                Log.d("ItemRepository","query ok " + collectionPath)

            }
            .addOnFailureListener { exception ->
                Log.d("ItemRepository", exception.toString())
            }

        return templatesLiveData
    }


    fun saveItemToCharacterEquipment(characterId: String, item: Item, onComplete: (Boolean) -> Unit) {
        val itemDocumentPath = "equipments/${characterId}_eq/items"

        // Utwórz lub zaktualizuj dokument dla postaci
        val characterDocument = firestore.collection("equipments").document("${characterId}_eq")
        characterDocument.get()
            .addOnSuccessListener { snapshot ->
                val existingCharacterId = snapshot.getString("character_id")
                if (existingCharacterId != null && existingCharacterId != characterId) {
                    onComplete(false) // Zwróć false, jeśli dokument już istnieje i ma inne character_id
                } else {
                    characterDocument.set(mapOf("character_id" to characterId), SetOptions.merge())

                    // Utwórz lub zaktualizuj dokument dla przedmiotu
                    val itemsCollection = firestore.collection(itemDocumentPath)
                    val newItemDocument = itemsCollection.document(item.item_name) // Zakładając, że `itemName` jest unikalnym identyfikatorem przedmiotu
                    newItemDocument.set(item)
                        .addOnSuccessListener {
                            Log.d("ItemRepository", "Item saved successfully: $itemDocumentPath")
                            onComplete(true) // Zwróć true, jeśli zapis się powiódł
                        }
                        .addOnFailureListener { exception ->
                            Log.e("ItemRepository", "Error saving item: $itemDocumentPath", exception)
                            onComplete(false) // Zwróć false w przypadku niepowodzenia zapisu
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.e("ItemRepository", "Error checking character document: $characterId", exception)
                onComplete(false) // Zwróć false w przypadku błędu sprawdzania dokumentu postaci
            }
    }



    fun saveItemToCharacterEquipmentInUse(characterId: String, item: Item, onComplete: (Boolean) -> Unit) {
        val itemDocumentPath = "equipments/${characterId}_eq/items_in_use"

        // Utwórz lub zaktualizuj dokument dla postaci
        val characterDocument = firestore.collection("equipments").document("${characterId}_eq")
        characterDocument.get()
            .addOnSuccessListener { snapshot ->
                val existingCharacterId = snapshot.getString("character_id")
                if (existingCharacterId != null && existingCharacterId != characterId) {
                    onComplete(false) // Zwróć false, jeśli dokument już istnieje i ma inne character_id
                } else {
                    characterDocument.set(mapOf("character_id" to characterId), SetOptions.merge())

                    // Utwórz lub zaktualizuj dokument dla przedmiotu
                    val itemsCollection = firestore.collection(itemDocumentPath)
                    val newItemDocument = itemsCollection.document(item.item_name) // Zakładając, że `itemName` jest unikalnym identyfikatorem przedmiotu
                    newItemDocument.set(item)
                        .addOnSuccessListener {
                            Log.d("ItemRepository", "Item saved successfully: $itemDocumentPath")
                            onComplete(true) // Zwróć true, jeśli zapis się powiódł
                        }
                        .addOnFailureListener { exception ->
                            Log.e("ItemRepository", "Error saving item: $itemDocumentPath", exception)
                            onComplete(false) // Zwróć false w przypadku niepowodzenia zapisu
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.e("ItemRepository", "Error checking character document: $characterId", exception)
                onComplete(false) // Zwróć false w przypadku błędu sprawdzania dokumentu postaci
            }
    }

    fun deleteItemFromCharacterEquipment(characterId: String, itemName: String, onComplete: (Boolean) -> Unit) {
        val itemDocumentPath = "equipments/${characterId}_eq/items"

        val itemDocument = firestore.collection(itemDocumentPath).document(itemName)
        itemDocument.delete()
            .addOnSuccessListener {
                Log.d("ItemRepository", "Item deleted successfully: $itemDocumentPath/$itemName")
                onComplete(true) // Zwróć true, jeśli usunięcie się powiodło
            }
            .addOnFailureListener { exception ->
                Log.e("ItemRepository", "Error deleting item: $itemDocumentPath/$itemName", exception)
                onComplete(false) // Zwróć false w przypadku błędu usuwania przedmiotu
            }
    }



    fun deleteItemFromCharacterEquipmentInUse(characterId: String, itemName: String, onComplete: (Boolean) -> Unit) {
        val itemDocumentPath = "equipments/${characterId}_eq/items_in_use"

        val itemDocument = firestore.collection(itemDocumentPath).document(itemName)
        itemDocument.delete()
            .addOnSuccessListener {
                Log.d("ItemRepository", "Item deleted successfully: $itemDocumentPath/$itemName")
                onComplete(true) // Zwróć true, jeśli usunięcie się powiodło
            }
            .addOnFailureListener { exception ->
                Log.e("ItemRepository", "Error deleting item: $itemDocumentPath/$itemName", exception)
                onComplete(false) // Zwróć false w przypadku błędu usuwania przedmiotu
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


}
