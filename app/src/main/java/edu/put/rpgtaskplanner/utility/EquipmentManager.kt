package edu.put.rpgtaskplanner.utility
import edu.put.rpgtaskplanner.model.Item

object EquipmentManager {
    private var currentItem: Item? = null

    private var equippedItems: MutableList<Item>? = mutableListOf()

    fun setCurrentItem(item: Item) {
        currentItem = item
    }

    fun getCurrentItem(): Item? {
        return currentItem
    }

    fun equipItem(item: Item)
    {
        equippedItems?.add(item)
    }

    fun unequipItem(item: Item)
    {
        equippedItems?.remove(item)
    }

    fun getEquippedItems(): MutableList<Item>?
    {
        return equippedItems
    }

    fun getMatchingIndicesAsBooleans(equipmentItemList: List<Item>): List<Boolean> {
        val matchingIndices = mutableListOf<Boolean>()

        for (equipmentItem in equipmentItemList) {
            val matchFound = equippedItems?.any { it.item_name == equipmentItem.item_name } ?: false
            matchingIndices.add(matchFound)
        }

        return matchingIndices
    }


    fun clearEquippedItems()
    {
        equippedItems = mutableListOf()
    }

    fun setToNull() {
        currentItem = null
    }
}