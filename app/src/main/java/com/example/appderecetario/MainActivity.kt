package com.example.appderecetario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appderecetario.presentation.home.HomeScreen
import com.example.appderecetario.presentation.onboarding.OnboardingScreen
import com.example.appderecetario.presentation.recipes.detail.RecipeDetailScreen
import com.example.appderecetario.presentation.splash.SplashScreen
import com.example.appderecetario.ui.theme.AppDeRecetarioTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppDeRecetarioTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "splash"
                ) {
                    composable("splash") { SplashScreen(navController) }
                    composable("onboarding") { OnboardingScreen(navController) }
                    composable("home") {
                        HomeScreen(
                            onRecipeSelected = { id ->
                                navController.navigate("detail/$id")
                            }
                        )
                    }
                    composable(
                        "detail/{recipeId}",
                        arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
                    ) { _ ->
                        RecipeDetailScreen(navController = navController)
                    }
                }
            }
        }
    }
}