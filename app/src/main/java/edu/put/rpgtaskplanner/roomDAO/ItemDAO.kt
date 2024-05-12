package edu.put.rpgtaskplanner.roomDAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import edu.put.rpgtaskplanner.model.Item
import edu.put.rpgtaskplanner.roomEntity.ItemEntity

@Dao
interface ItemDAO {
    @Query("SELECT * FROM ItemEntity WHERE character_id = :characterId")
    fun getAllForCharacter(characterId: String): List<ItemEntity>

    @Query("SELECT * FROM ItemEntity WHERE character_id = :characterId AND item_name = :itemName")
    fun getItemForCharacter(characterId: String, itemName: String): ItemEntity?

    @Query("DELETE FROM ItemEntity WHERE character_id = :characterId AND item_name = :itemName")
    fun deleteItemForCharacter(characterId: String, itemName: String)

    @Query("DELETE FROM ItemEntity WHERE character_id = :characterId")
    fun deleteAllForCharacter(characterId: String)

    @Insert
    fun insertItemForCharacter(item: ItemEntity)


}