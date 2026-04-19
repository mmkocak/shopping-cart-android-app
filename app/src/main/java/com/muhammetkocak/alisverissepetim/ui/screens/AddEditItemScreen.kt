package com.muhammetkocak.alisverissepetim.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.muhammetkocak.alisverissepetim.ui.viewmodel.ShoppingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditItemScreen(
    viewModel: ShoppingViewModel,
    scannedBarcode: String?,
    onNavigateBack: () -> Unit,
    onNavigateToScanner: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var barcode by remember { mutableStateOf(scannedBarcode ?: "") }
    var priceStr by remember { mutableStateOf("") }
    var storeName by remember { mutableStateOf("") }

    // Update barcode if a new one is scanned
    LaunchedEffect(scannedBarcode) {
        if (scannedBarcode != null) {
            barcode = scannedBarcode
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Yeni Ürün Ekle") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Ürün Adı") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = barcode,
                    onValueChange = { barcode = it },
                    label = { Text("Barkod") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                IconButton(
                    onClick = onNavigateToScanner,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Icon(Icons.Default.DocumentScanner, contentDescription = "Barkod Tara", modifier = Modifier.size(32.dp))
                }
            }

            OutlinedTextField(
                value = priceStr,
                onValueChange = { priceStr = it },
                label = { Text("Fiyat (₺)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true
            )

            OutlinedTextField(
                value = storeName,
                onValueChange = { storeName = it },
                label = { Text("Mağaza Adı (İsteğe Bağlı)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val price = priceStr.toDoubleOrNull() ?: 0.0
                    if (name.isNotBlank()) {
                        viewModel.addItem(name, barcode.takeIf { it.isNotBlank() }, price, storeName.takeIf { it.isNotBlank() })
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() && priceStr.isNotBlank()
            ) {
                Text("Kaydet")
            }
        }
    }
}
