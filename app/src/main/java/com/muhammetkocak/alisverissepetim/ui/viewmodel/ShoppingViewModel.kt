package com.muhammetkocak.alisverissepetim.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammetkocak.alisverissepetim.data.local.entity.ShoppingItem
import com.muhammetkocak.alisverissepetim.data.repository.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repository: ShoppingRepository
) : ViewModel() {

    val allItems: StateFlow<List<ShoppingItem>> = repository.getAllItems()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _comparedItems = MutableStateFlow<List<ShoppingItem>>(emptyList())
    val comparedItems: StateFlow<List<ShoppingItem>> = _comparedItems

    fun addItem(name: String, barcode: String?, price: Double, storeName: String?) {
        viewModelScope.launch {
            repository.insertItem(
                ShoppingItem(
                    name = name,
                    barcode = barcode,
                    price = price,
                    storeName = storeName
                )
            )
        }
    }

    fun updateItem(item: ShoppingItem) {
        viewModelScope.launch {
            repository.updateItem(item)
        }
    }

    fun deleteItem(item: ShoppingItem) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }

    fun searchPricesByBarcode(barcode: String) {
        viewModelScope.launch {
            repository.getItemsByBarcode(barcode).collect {
                _comparedItems.value = it
            }
        }
    }
}
