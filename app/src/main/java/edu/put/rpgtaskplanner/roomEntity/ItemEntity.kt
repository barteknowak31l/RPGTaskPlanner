package edu.put.rpgtaskplanner.roomEntity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["item_name", "character_id"])
data class ItemEntity(
    val item_name: String,
    var character_id: String,
    @ColumnInfo(name = "base_bonus") val base_bonus: Double?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "image_resource_id") val image_resource_id: Int?,
    @ColumnInfo(name = "level") val level: Int?,
    @ColumnInfo(name = "price") val price: Double?,
    @ColumnInfo(name = "type") val type: Int?,
)