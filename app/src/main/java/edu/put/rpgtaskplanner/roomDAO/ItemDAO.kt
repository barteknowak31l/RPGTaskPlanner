package edu.put.rpgtaskplanner.roomDAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import edu.put.rpgtaskplanner.model.Item
import edu.put.rpgtaskplanner.roomEntity.ItemEntity

@Dao
interface ItemDAO {
    @Query("SELECT * FROM itementity")
    fun getAll(): List<Item>

    @Insert
    fun insertAll(vararg items: ItemEntity)

    @Delete
    fun delete(item: ItemEntity)

    @Query("DELETE FROM itementity")
    fun deleteAll()
}