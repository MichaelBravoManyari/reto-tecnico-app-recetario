package com.example.appderecetario.usecase

import com.example.appderecetario.data.repository.RecipeRepository
import com.example.appderecetario.domain.model.Recipe
import com.example.appderecetario.domain.usecase.ToggleFavoriteRecipeUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ToggleFavoriteRecipeUseCaseTest {
    private val repository: RecipeRepository = mockk(relaxed = true)
    private lateinit var useCase: ToggleFavoriteRecipeUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        useCase = ToggleFavoriteRecipeUseCase(repository)
    }

    @Test
    fun `si la receta no es favorita, se agrega a favoritos`() = runTest(testDispatcher) {
        val recipe = Recipe(
            id = 1,
            name = "Tacos",
            isFavorite = false,
            ingredients = listOf(),
            image = "",
            steps = listOf(),
            description = ""
        )

        useCase(recipe)

        coVerify { repository.addFavorite(recipe) }
        coVerify(exactly = 0) { repository.removeFavorite(any()) }
    }

    @Test
    fun `si la receta es favorita, se elimina de favoritos`() = runTest(testDispatcher) {
        val recipe = Recipe(
            id = 1, name = "Tacos", isFavorite = true, ingredients = listOf(),
            image = "",
            steps = listOf(),
            description = ""
        )

        useCase(recipe)

        coVerify { repository.removeFavorite(recipe) }
        coVerify(exactly = 0) { repository.addFavorite(any()) }
    }
}