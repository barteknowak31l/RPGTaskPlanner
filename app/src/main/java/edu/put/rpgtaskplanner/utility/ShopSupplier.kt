package edu.put.rpgtaskplanner.utility

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.room.Room
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.put.rpgtaskplanner.model.CharacterClass
import edu.put.rpgtaskplanner.model.Item
import edu.put.rpgtaskplanner.model.ItemType
import edu.put.rpgtaskplanner.repository.ItemRepository
import edu.put.rpgtaskplanner.roomDatabase.ItemDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger

class ShopSupplier (val context: Context, val lifecycleOwner: LifecycleOwner,
                    private val lifecycleScope: CoroutineScope
)
{

    interface OnDeleteItemListener {
        fun onDeleteItem(item: Item)
    }

    companion object
    {
        var listeners: List<OnDeleteItemListener> = mutableListOf()
    }



    interface RefreshShopCallback {
        fun onRefreshFinished()
        fun onShopFetchedFromDatabase(itemList: List<Item>)

    }

    private val db = Room.databaseBuilder(
        context,
        ItemDatabase::class.java, "database-item"
    ).fallbackToDestructiveMigration()
        .build()

    private val firedb = Firebase.firestore
    private val itemRepository = ItemRepository(firedb)
    private val itemDAO = db.itemDao()

    private var artifact = Item()
    private var armour = Item()
    private var belt = Item()
    private var boots = Item()
    private var helmet = Item()
    private var offhand = Item()
    private var ring = Item()
    private var weapon = Item()
    fun refreshShop(callback: RefreshShopCallback?) {
        val character = CharacterManager.getCurrentCharacter()
        val itemCount = AtomicInteger(8)

        if (character != null) {

            CharacterClass.fromId(character.character_class)
                ?.let {
                    getItemByTypeAndClassFromFirestore(ItemType.ARTIFACT, it) { item ->
                        if (item != null) {
                            artifact = item
                            artifact.base_bonus *= character.level
                            checkAndCallCallback(callback, itemCount)
                            Log.d("ShopSupplier", "Pobrano artifact " + item.item_name + " lol " + artifact.item_name)
                        }
                    }
                }

            CharacterClass.fromId(character.character_class)
                ?.let {
                    getItemByTypeAndClassFromFirestore(ItemType.ARMOUR, it) { item ->
                        if (item != null) {
                            armour = item
                            armour.base_bonus *= character.level

                            checkAndCallCallback(callback, itemCount)
                            Log.d("ShopSupplier", "Pobrano armour")

                        }
                    }
                }

            CharacterClass.fromId(character.character_class)
                ?.let {
                    getItemByTypeAndClassFromFirestore(ItemType.BELT, it) { item ->
                        if (item != null) {
                            belt = item
                            belt.base_bonus *= character.level

                            checkAndCallCallback(callback, itemCount)
                            Log.d("ShopSupplier", "Pobrano belt")

                        }
                    }
                }

            CharacterClass.fromId(character.character_class)
                ?.let {
                    getItemByTypeAndClassFromFirestore(ItemType.BOOTS, it) { item ->
                        if (item != null) {
                            boots = item
                            boots.base_bonus *= character.level

                            checkAndCallCallback(callback, itemCount)
                            Log.d("ShopSupplier", "Pobrano boots")

                        }
                    }
                }

            CharacterClass.fromId(character.character_class)
                ?.let {
                    getItemByTypeAndClassFromFirestore(ItemType.HELMET, it) { item ->
                        if (item != null) {
                            helmet = item
                            helmet.base_bonus *= character.level

                            checkAndCallCallback(callback, itemCount)
                            Log.d("ShopSupplier", "Pobrano helmet")

                        }
                    }
                }

            CharacterClass.fromId(character.character_class)
                ?.let {
                    getItemByTypeAndClassFromFirestore(ItemType.OFFHAND, it) { item ->
                        if (item != null) {
                            offhand = item
                            offhand.base_bonus *= character.level

                            checkAndCallCallback(callback, itemCount)
                            Log.d("ShopSupplier", "Pobrano offhand")

                        }
                    }
                }

            CharacterClass.fromId(character.character_class)
                ?.let {
                    getItemByTypeAndClassFromFirestore(ItemType.RING, it) { item ->
                        if (item != null) {
                            ring = item
                            ring.base_bonus *= character.level

                            checkAndCallCallback(callback, itemCount)
                            Log.d("ShopSupplier", "Pobrano ring")

                        }
                    }
                }

            CharacterClass.fromId(character.character_class)
                ?.let {
                    getItemByTypeAndClassFromFirestore(ItemType.WEAPON, it) { item ->
                        if (item != null) {
                            weapon = item
                            weapon.base_bonus *= character.level

                            checkAndCallCallback(callback, itemCount)
                            Log.d("ShopSupplier", "Pobrano weapon")

                        }
                    }
                }
        }
    }

    private fun getItemByTypeAndClassFromFirestore(type: ItemType, characterClass: CharacterClass, callback: (Item?) -> Unit) {

        itemRepository.getItemTemplatesByTypeAndClass(type, characterClass)
            .observe(lifecycleOwner) { items ->
                if (items.isNotEmpty()) {
                    val randomIndex = items.indices.random()

                    val randomItem = items[randomIndex]

                    callback(randomItem)

                } else {

                    callback(null)
                }
            }
    }


    private fun checkAndCallCallback(callback: RefreshShopCallback?, itemCount: AtomicInteger) {
        if (itemCount.decrementAndGet() == 0) {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    itemDAO.deleteAll()

                    val artifactEntity = Item.toEntity(artifact)
                    val armourEntity = Item.toEntity(armour)
                    val beltEntity = Item.toEntity(belt)
                    val bootsEntity = Item.toEntity(boots)
                    val helmetEntity = Item.toEntity(helmet)
                    val offhandEntity = Item.toEntity(offhand)
                    val ringEntity = Item.toEntity(ring)
                    val weaponEntity = Item.toEntity(weapon)
                    val itemsToInsert = arrayOf(
                        artifactEntity, armourEntity, beltEntity, bootsEntity,
                        helmetEntity, offhandEntity, ringEntity, weaponEntity
                    )
                    itemDAO.insertAll(*itemsToInsert)
                }

                callback?.onRefreshFinished()
            }
        }
    }


    fun itemList(): List<Item>
    {
        return listOf(artifact,armour,belt,boots,helmet,offhand,ring,weapon)
    }

    fun fetchShopFromLocalDb(callback: RefreshShopCallback?) {
            lifecycleScope.launch {
                val itemList = withContext(Dispatchers.IO) {
                    itemDAO.getAll()
                }

                callback?.onShopFetchedFromDatabase(itemList)
        }
    }

    fun deleteItemFromShop(item: Item) {
        lifecycleScope.launch {

            val itemEntity = Item.toEntity(item)
            val deletedEntity = withContext(Dispatchers.IO) {
                itemDAO.delete(itemEntity)

            }
            listeners.stream().forEach{ it.onDeleteItem(item)}
        }
    }


}