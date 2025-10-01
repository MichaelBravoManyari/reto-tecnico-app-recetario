package com.example.appderecetario.usecase

import com.example.appderecetario.data.repository.RecipeRepository
import com.example.appderecetario.domain.model.Recipe
import com.example.appderecetario.domain.usecase.GetFavoriteRecipesUseCase
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetFavoriteRecipesUseCaseTest {
    private val repository: RecipeRepository = mockk()
    private lateinit var useCase: GetFavoriteRecipesUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        useCase = GetFavoriteRecipesUseCase(repository)
    }

    @Test
    fun `retorna lista de favoritos desde el repositorio`() = runTest(testDispatcher) {
        val recipes = listOf(
            Recipe(
                id = 1, name = "Pizza", isFavorite = true, ingredients = listOf(),
                image = "",
                steps = listOf(),
                description = ""
            )
        )
        every { repository.getFavorites() } returns flowOf(recipes)

        val result = useCase().first()

        assertEquals(1, result.size)
        assertEquals("Pizza", result.first().name)
    }
}