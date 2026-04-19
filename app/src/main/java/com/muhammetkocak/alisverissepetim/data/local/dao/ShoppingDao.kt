package com.muhammetkocak.alisverissepetim.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.muhammetkocak.alisverissepetim.data.local.entity.ShoppingItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {

    @Query("SELECT * FROM shopping_items ORDER BY addedDate DESC")
    fun getAllItems(): Flow<List<ShoppingItem>>

    @Query("SELECT * FROM shopping_items WHERE barcode = :barcode")
    fun getItemsByBarcode(barcode: String): Flow<List<ShoppingItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ShoppingItem)

    @Update
    suspend fun updateItem(item: ShoppingItem)

    @Delete
    suspend fun deleteItem(item: ShoppingItem)
}
