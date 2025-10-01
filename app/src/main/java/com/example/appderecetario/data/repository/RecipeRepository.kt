package com.example.appderecetario.data.repository

import com.example.appderecetario.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getRecipes(): List<Recipe>
    fun getFavorites(): Flow<List<Recipe>>
    suspend fun addFavorite(recipe: Recipe)
    suspend fun removeFavorite(recipe: Recipe)
}