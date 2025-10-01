package com.example.appderecetario.usecase

import com.example.appderecetario.data.repository.RecipeRepository
import com.example.appderecetario.domain.model.Recipe
import com.example.appderecetario.domain.usecase.GetRecipesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetRecipesUseCaseTest {

    private val repository: RecipeRepository = mockk()
    private lateinit var useCase: GetRecipesUseCase

    @Before
    fun setUp() {
        useCase = GetRecipesUseCase(repository)
    }

    @Test
    fun `cuando el repo retorna recetas, el usecase las devuelve`() = runTest {
        val recipes = listOf(
            Recipe(1, "Tacos", "", "desc", emptyList(), emptyList(), false),
            Recipe(2, "Pizza", "", "desc", emptyList(), emptyList(), false)
        )
        coEvery { repository.getRecipes() } returns recipes

        val result = useCase()

        assertEquals(2, result.size)
        assertEquals("Tacos", result[0].name)
        assertEquals("Pizza", result[1].name)
    }
}