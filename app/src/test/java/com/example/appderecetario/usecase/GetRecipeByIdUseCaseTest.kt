package com.example.appderecetario.usecase

import com.example.appderecetario.data.repository.RecipeRepository
import com.example.appderecetario.domain.model.Recipe
import com.example.appderecetario.domain.usecase.GetRecipeByIdUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetRecipeByIdUseCaseTest {
    private val repository: RecipeRepository = mockk()
    private lateinit var useCase: GetRecipeByIdUseCase

    @Before
    fun setUp() {
        useCase = GetRecipeByIdUseCase(repository)
    }

    @Test
    fun `retorna receta si existe`() = runTest {
        val recipe = Recipe(1, "Tacos", "", "desc", emptyList(), emptyList(), false)
        coEvery { repository.getRecipes() } returns listOf(recipe)

        val result = useCase(1)

        assertNotNull(result)
        assertEquals("Tacos", result?.name)
    }

    @Test
    fun `retorna null si no existe`() = runTest {
        coEvery { repository.getRecipes() } returns emptyList()

        val result = useCase(999)

        assertNull(result)
    }
}