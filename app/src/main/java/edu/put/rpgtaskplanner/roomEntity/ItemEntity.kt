package edu.put.rpgtaskplanner.roomEntity

import android.annotation.SuppressLint
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.annotation.NonNull;

@Entity
data class ItemEntity(
    @PrimaryKey
    val item_name: String,
    @ColumnInfo(name = "bonus") val bonus: Double?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "image_resource_id") val image_resource_id: Int?,
    @ColumnInfo(name = "level") val level: Int?,
    @ColumnInfo(name = "price") val price: Double?,
    @ColumnInfo(name = "type") val type: Int?,
)