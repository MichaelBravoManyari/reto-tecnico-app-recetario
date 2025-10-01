package com.example.appderecetario.domain.usecase

import com.example.appderecetario.data.repository.RecipeRepository
import com.example.appderecetario.domain.model.Recipe
import javax.inject.Inject

class GetRecipeByIdUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(id: Int): Recipe? {
        return repository.getRecipes().firstOrNull { it.id == id }
    }
}