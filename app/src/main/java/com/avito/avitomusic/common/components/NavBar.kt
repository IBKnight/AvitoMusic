package com.avito.avitomusic.common.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.avito.avitomusic.R

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem(Routes.SEARCH.localizedRouteName(), Routes.SEARCH.route, Icons.Default.Search),
        BottomNavItem(Routes.SAVED .localizedRouteName(), Routes.SAVED.route, Icons.Default.Home),
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(text = item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

enum class Routes(val route: String) {
    SEARCH("search"),
    SAVED("saved"),
    PLAYER("player");

    @Composable
    fun localizedRouteName(): String {
        return when (this) {
            SEARCH -> stringResource(id = R.string.search)
            SAVED -> stringResource(id = R.string.saved)
//            PLAYER -> stringResource(id = R.string.player)
            else -> ""
        }
    }
}

data class BottomNavItem(
    val title: String,
    val route: String,
    val icon: ImageVector
)