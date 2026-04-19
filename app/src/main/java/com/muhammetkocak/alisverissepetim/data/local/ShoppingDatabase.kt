package com.muhammetkocak.alisverissepetim.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muhammetkocak.alisverissepetim.data.local.dao.ShoppingDao
import com.muhammetkocak.alisverissepetim.data.local.entity.ShoppingItem

@Database(entities = [ShoppingItem::class], version = 1, exportSchema = false)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract val shoppingDao: ShoppingDao
}
