package com.muhammetkocak.alisverissepetim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.muhammetkocak.alisverissepetim.ui.camera.BarcodeScannerScreen
import com.muhammetkocak.alisverissepetim.ui.screens.AddEditItemScreen
import com.muhammetkocak.alisverissepetim.ui.screens.PriceComparisonScreen
import com.muhammetkocak.alisverissepetim.ui.screens.ShoppingListScreen
import com.muhammetkocak.alisverissepetim.ui.theme.AlisverissepetimTheme
import com.muhammetkocak.alisverissepetim.ui.viewmodel.ShoppingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlisverissepetimTheme {
                val navController = rememberNavController()
                ShoppingAppNavHost(navController = navController)
            }
        }
    }
}

@Composable
fun ShoppingAppNavHost(navController: NavHostController) {
    val viewModel: ShoppingViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "shopping_list") {
        composable("shopping_list") {
            ShoppingListScreen(
                viewModel = viewModel,
                onNavigateToAddItem = { navController.navigate("add_edit_item") },
                onNavigateToCompare = { barcode -> navController.navigate("price_compare/$barcode") }
            )
        }

        composable("add_edit_item") { backStackEntry ->
            val scannedBarcode = backStackEntry.savedStateHandle.get<String>("scanned_barcode")
            AddEditItemScreen(
                viewModel = viewModel,
                scannedBarcode = scannedBarcode,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToScanner = { navController.navigate("barcode_scanner") }
            )
        }

        composable("barcode_scanner") {
            BarcodeScannerScreen(
                onBarcodeDetected = { barcode ->
                    // Set result in previous backstack entry
                    navController.previousBackStackEntry?.savedStateHandle?.set("scanned_barcode", barcode)
                    navController.popBackStack()
                }
            )
        }

        composable("price_compare/{barcode}") { backStackEntry ->
            val barcode = backStackEntry.arguments?.getString("barcode") ?: ""
            PriceComparisonScreen(
                barcode = barcode,
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
