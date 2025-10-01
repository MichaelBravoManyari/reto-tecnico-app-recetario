package com.example.appderecetario.data.repository

import com.example.appderecetario.data.local.dao.RecipeDao
import com.example.appderecetario.data.local.entity.RecipeEntity
import com.example.appderecetario.data.mapper.toDomain
import com.example.appderecetario.data.remote.api.RecipeApi
import com.example.appderecetario.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val api: RecipeApi,
    private val dao: RecipeDao
) : RecipeRepository {
    override suspend fun getRecipes(): List<Recipe> {
        return api.getRecipes().map { it.toDomain() }
    }

    override fun getFavorites(): Flow<List<Recipe>> {
        return dao.getFavorites().map { list ->
            list.map {
                Recipe(
                    id = it.id,
                    name = it.name,
                    image = it.image,
                    description = it.description,
                    ingredients = emptyList(),
                    steps = emptyList(),
                    isFavorite = true
                )
            }
        }
    }

    override suspend fun addFavorite(recipe: Recipe) {
        dao.insertFavorite(
            RecipeEntity(recipe.id, recipe.name, recipe.image, recipe.description)
        )
    }

    override suspend fun removeFavorite(recipe: Recipe) {
        dao.deleteFavorite(
            RecipeEntity(recipe.id, recipe.name, recipe.image, recipe.description)
        )
    }
}