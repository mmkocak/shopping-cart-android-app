package com.muhammetkocak.alisverissepetim.data.repository

import com.muhammetkocak.alisverissepetim.data.local.dao.ShoppingDao
import com.muhammetkocak.alisverissepetim.data.local.entity.ShoppingItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShoppingRepository @Inject constructor(
    private val dao: ShoppingDao
) {

    fun getAllItems(): Flow<List<ShoppingItem>> {
        return dao.getAllItems()
    }

    fun getItemsByBarcode(barcode: String): Flow<List<ShoppingItem>> {
        return dao.getItemsByBarcode(barcode)
    }

    suspend fun insertItem(item: ShoppingItem) {
        dao.insertItem(item)
    }

    suspend fun updateItem(item: ShoppingItem) {
        dao.updateItem(item)
    }

    suspend fun deleteItem(item: ShoppingItem) {
        dao.deleteItem(item)
    }
}
