package edu.put.rpgtaskplanner.roomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.put.rpgtaskplanner.roomDAO.ItemDAO
import edu.put.rpgtaskplanner.roomEntity.ItemEntity

@Database(entities = [ItemEntity::class], version = 1)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDAO
}