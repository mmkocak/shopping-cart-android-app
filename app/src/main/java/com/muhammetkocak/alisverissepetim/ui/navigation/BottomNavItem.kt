package com.muhammetkocak.alisverissepetim.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var title: String, var icon: ImageVector, var route: String) {
    object Dashboard : BottomNavItem("Ana Sayfa", Icons.Default.Home, "dashboard")
    object Lists : BottomNavItem("Listeler", Icons.Default.List, "shopping_list")
    object Settings : BottomNavItem("Ayarlar", Icons.Default.Settings, "settings")
}
