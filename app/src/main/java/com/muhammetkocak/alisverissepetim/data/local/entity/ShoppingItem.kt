package com.muhammetkocak.alisverissepetim.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_items")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val barcode: String?,
    val price: Double,
    val storeName: String?,
    val isChecked: Boolean = false,
    val addedDate: Long = System.currentTimeMillis()
)
