package edu.put.rpgtaskplanner.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import edu.put.rpgtaskplanner.model.Item

class ItemRepository(private val firestore: FirebaseFirestore) {

    private val collection = firestore.collection("equipments")

    enum class ItemType {
        ARMOUR,
        ARTIFACT,
        BELT,
        BOOTS,
        HELMET,
        OFFHAND,
        RING,
        WEAPON
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

    fun updateItem(characterId: String, itemName: String, updates: Map<String, Any>, onComplete: (Boolean) -> Unit) {
        val itemDocument = collection.document("${characterId}_eq")
            .collection("items")
            .document(itemName)

        itemDocument.update(updates)
            .addOnSuccessListener {
                val itemInUseDocument = collection.document("${characterId}_eq")
                    .collection("items_in_use")
                    .document(itemName)

                itemInUseDocument.update(updates)
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
}
