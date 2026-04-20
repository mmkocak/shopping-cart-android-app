package com.muhammetkocak.alisverissepetim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.muhammetkocak.alisverissepetim.ui.camera.BarcodeScannerScreen
import com.muhammetkocak.alisverissepetim.ui.navigation.BottomNavItem
import com.muhammetkocak.alisverissepetim.ui.screens.AddEditItemScreen
import com.muhammetkocak.alisverissepetim.ui.screens.DashboardScreen
import com.muhammetkocak.alisverissepetim.ui.screens.PriceComparisonScreen
import com.muhammetkocak.alisverissepetim.ui.screens.SettingsScreen
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
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val viewModel: ShoppingViewModel = hiltViewModel()

    val items = listOf(
        BottomNavItem.Dashboard,
        BottomNavItem.Lists,
        BottomNavItem.Settings
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showBottomBar = items.any { it.route == currentDestination?.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ) {
                    items.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.title) },
                            label = { Text(item.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Dashboard.route) {
                DashboardScreen(
                    viewModel = viewModel,
                    onNavigateToLists = {
                        navController.navigate(BottomNavItem.Lists.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
            composable(BottomNavItem.Lists.route) {
                ShoppingListScreen(
                    viewModel = viewModel,
                    onNavigateToAddItem = { navController.navigate("add_edit_item") },
                    onNavigateToCompare = { barcode -> navController.navigate("price_compare/$barcode") }
                )
            }
            composable(BottomNavItem.Settings.route) {
                SettingsScreen()
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
}
