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
        val user = UserManager.getCurrentUser()
        val itemCount = AtomicInteger(8)

        if (character != null && user != null) {

            CharacterClass.fromId(character.character_class)
                ?.let {
                    getItemByTypeAndClassFromFirestore(ItemType.ARTIFACT, it) { item ->
                        if (item != null) {
                            artifact = item
                            artifact.base_bonus = CharacterBuilder.BASE_ENERGY_REGEN + artifact.base_bonus * character.level
                            artifact.base_bonus = Math.round(artifact.base_bonus * 100.0)/ 100.0
                            artifact.image_resource_id = context.resources.getIdentifier(artifact.image_resource_name,"drawable",context.packageName)
                            checkAndCallCallback(callback, itemCount, user.character_id)
                        }
                    }
                }

            CharacterClass.fromId(character.character_class)
                ?.let {
                    getItemByTypeAndClassFromFirestore(ItemType.ARMOUR, it) { item ->
                        if (item != null) {
                            armour = item
                            armour.base_bonus = CharacterBuilder.BASE_HEALTH + armour.base_bonus * character.level
                            armour.base_bonus = Math.round(armour.base_bonus * 100.0)/ 100.0
                            armour.image_resource_id = context.resources.getIdentifier(armour.image_resource_name,"drawable",context.packageName)

                            checkAndCallCallback(callback, itemCount, user.character_id)
                        }
                    }
                }

            CharacterClass.fromId(character.character_class)
                ?.let {
                    getItemByTypeAndClassFromFirestore(ItemType.BELT, it) { item ->
                        if (item != null) {
                            belt = item
                            belt.base_bonus = CharacterBuilder.BASE_GOLD_MULTIPLIER + belt.base_bonus * character.level
                            belt.base_bonus = Math.round(belt.base_bonus * 100.0)/ 100.0
                            belt.image_resource_id = context.resources.getIdentifier(belt.image_resource_name,"drawable",context.packageName)

                            checkAndCallCallback(callback, itemCount, user.character_id)
                        }
                    }
                }

            CharacterClass.fromId(character.character_class)
                ?.let {
                    getItemByTypeAndClassFromFirestore(ItemType.BOOTS, it) { item ->
                        if (item != null) {
                            boots = item
                            boots.base_bonus = CharacterBuilder.BASE_EXP_MULTIPLIER + boots.base_bonus * character.level
                            boots.base_bonus = Math.round(boots.base_bonus * 100.0)/ 100.0
                            boots.image_resource_id = context.resources.getIdentifier(boots.image_resource_name,"drawable",context.packageName)

                            checkAndCallCallback(callback, itemCount, user.character_id)
                        }
                    }
                }

            CharacterClass.fromId(character.character_class)
                ?.let {
                    getItemByTypeAndClassFromFirestore(ItemType.HELMET, it) { item ->
                        if (item != null) {
                            helmet = item
                            helmet.base_bonus = CharacterBuilder.BASE_ENERGY + helmet.base_bonus * character.level
                            helmet.base_bonus = Math.round(helmet.base_bonus * 100.0)/ 100.0
                            helmet.image_resource_id = context.resources.getIdentifier(helmet.image_resource_name,"drawable",context.packageName)

                            checkAndCallCallback(callback, itemCount, user.character_id)
                        }
                    }
                }

            CharacterClass.fromId(character.character_class)
                ?.let {
                    getItemByTypeAndClassFromFirestore(ItemType.OFFHAND, it) { item ->
                        if (item != null) {
                            offhand = item
                            offhand.base_bonus = CharacterBuilder.BASE_GOLD_MULTIPLIER + offhand.base_bonus * character.level
                            offhand.base_bonus = Math.round(offhand.base_bonus * 100.0)/ 100.0
                            offhand.image_resource_id = context.resources.getIdentifier(offhand.image_resource_name,"drawable",context.packageName)

                            checkAndCallCallback(callback, itemCount, user.character_id)

                        }
                    }
                }

            CharacterClass.fromId(character.character_class)
                ?.let {
                    getItemByTypeAndClassFromFirestore(ItemType.RING, it) { item ->
                        if (item != null) {
                            ring = item
                            ring.base_bonus = CharacterBuilder.BASE_HEALTH_REGEN + ring.base_bonus * character.level
                            ring.base_bonus = Math.round(ring.base_bonus * 100.0)/ 100.0
                            ring.image_resource_id = context.resources.getIdentifier(ring.image_resource_name,"drawable",context.packageName)

                            checkAndCallCallback(callback, itemCount, user.character_id)
                        }
                    }
                }

            CharacterClass.fromId(character.character_class)
                ?.let {
                    getItemByTypeAndClassFromFirestore(ItemType.WEAPON, it) { item ->
                        if (item != null) {
                            weapon = item
                            weapon.base_bonus = CharacterBuilder.BASE_COOLDOWN + weapon.base_bonus * character.level
                            weapon.base_bonus = Math.round(weapon.base_bonus * 100.0)/ 100.0
                            weapon.image_resource_id = context.resources.getIdentifier(weapon.image_resource_name,"drawable",context.packageName)

                            checkAndCallCallback(callback, itemCount,user.character_id)
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


    private fun checkAndCallCallback(callback: RefreshShopCallback?, itemCount: AtomicInteger, characterId: String) {
        if (itemCount.decrementAndGet() == 0) {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    itemDAO.deleteAllForCharacter(characterId)

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

                    itemsToInsert.forEach {
                        it.character_id = characterId
                        itemDAO.insertItemForCharacter(it) }

                }

                callback?.onRefreshFinished()
            }
        }
    }


    fun itemList(): List<Item>
    {
        return listOf(artifact,armour,belt,boots,helmet,offhand,ring,weapon)
    }

    fun fetchShopFromLocalDb(callback: RefreshShopCallback?, characterId: String) {
            lifecycleScope.launch {
                val itemList = withContext(Dispatchers.IO) {
                    itemDAO.getAllForCharacter(characterId)
                }

                val items = itemList.map { Item.fromEntity(it) }

                callback?.onShopFetchedFromDatabase(items)
        }
    }

    fun deleteItemFromShop(item: Item, characterId: String) {
        lifecycleScope.launch {
            val deletedEntity = withContext(Dispatchers.IO) {
                itemDAO.deleteItemForCharacter(characterId,item.item_name)
            }
            listeners.stream().forEach{ it.onDeleteItem(item)}
        }
    }
}