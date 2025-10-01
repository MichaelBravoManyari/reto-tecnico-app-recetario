package com.example.appderecetario.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.appderecetario.presentation.recipes.favorites.FavoritesScreen
import com.example.appderecetario.presentation.recipes.list.RecipeListScreen

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

@Composable
fun HomeScreen(
    onRecipeSelected: (Int) -> Unit
) {
    val innerNavController = rememberNavController()

    val bottomNavItems = listOf(
        BottomNavItem("recipes", "Recetas", Icons.Default.Home),
        BottomNavItem("favorites", "Favoritos", Icons.Default.Favorite)
    )

    val navBackStackEntry by innerNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEach { item ->
                    val selected = currentRoute == item.route
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            if (currentRoute != item.route) {
                                innerNavController.navigate(item.route) {
                                    popUpTo(innerNavController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = innerNavController,
                startDestination = "recipes"
            ) {
                composable("recipes") {
                    RecipeListScreen(
                        onRecipeClick = { recipe ->
                            onRecipeSelected(recipe.id)
                        }
                    )
                }
                composable("favorites") {
                    FavoritesScreen(
                        onRecipeClick = { recipe ->
                            onRecipeSelected(recipe.id)
                        }
                    )
                }
            }
        }
    }
}
