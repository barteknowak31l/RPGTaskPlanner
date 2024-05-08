package edu.put.rpgtaskplanner.utility
import edu.put.rpgtaskplanner.model.Item

object EquipmentManager {
    private var currentItem: Item? = null

    fun setCurrentItem(item: Item) {
        currentItem = item
    }

    fun getCurrentItem(): Item? {
        return currentItem
    }

    fun setToNull() {
        currentItem = null
    }
}