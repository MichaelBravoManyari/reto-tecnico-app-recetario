package com.example.appderecetario.usecase

import com.example.appderecetario.data.repository.RecipeRepository
import com.example.appderecetario.domain.model.Recipe
import com.example.appderecetario.domain.usecase.RemoveFavoriteRecipeUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RemoveFavoriteRecipeUseCaseTest {
    private val repository: RecipeRepository = mockk(relaxed = true)
    private lateinit var removeUseCase: RemoveFavoriteRecipeUseCase

    @Before
    fun setUp() {
        removeUseCase = RemoveFavoriteRecipeUseCase(repository)
    }

    @Test
    fun `removeFavorite delega al repositorio`() = runTest {
        val recipe = Recipe(1, "Tacos", "", "desc", emptyList(), emptyList(), true)

        removeUseCase(recipe)

        coVerify { repository.removeFavorite(recipe) }
    }
}