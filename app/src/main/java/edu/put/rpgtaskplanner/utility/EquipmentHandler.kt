package edu.put.rpgtaskplanner.utility

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.put.rpgtaskplanner.model.Item
import edu.put.rpgtaskplanner.repository.ItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class EquipmentHandler(val context: Context, val lifecycleOwner: LifecycleOwner,
                       private val lifecycleScope: CoroutineScope
) {


    interface EquipmentHandlerCallback{
        fun onItemEquipped(item: Item?)
    }

    private val db = Firebase.firestore
    private val itemRepository = ItemRepository(db)

    // equip - save item to items in use
    // unequip - delete item from items in use


    fun equipItem(characterId: String, item: Item, callback: EquipmentHandlerCallback?) {

        lifecycleScope.launch {

            // get wszystkich, filtr po typie, jesli nnull unequip
            itemRepository.getItemFromCharacterEquipmentInUseFilterByType(characterId, item.type) { currentEquipped ->
                if (currentEquipped != null) {
                    unequipItem(characterId, item, null)
                }

                lifecycleScope.launch {
                    itemRepository.saveItemToCharacterEquipmentInUse(characterId, item) { success ->
                        if (success) {
                            callback?.onItemEquipped(item)
                        } else {
                            callback?.onItemEquipped(null)
                        }
                    }
                }
            }
        }
    }

    fun unequipItem(characterId: String, item: Item, callback: EquipmentHandlerCallback? )
    {
        lifecycleScope.launch {
            itemRepository.deleteItemFromCharacterEquipmentInUse(characterId, item.item_name) {success ->
                if(success)
                {
                    callback?.onItemEquipped(item)
                } else {
                    callback?.onItemEquipped(null)
                }

            }
        }
    }

}



