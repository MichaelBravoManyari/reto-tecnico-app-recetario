package com.example.appderecetario.usecase

import com.example.appderecetario.data.repository.RecipeRepository
import com.example.appderecetario.domain.model.Recipe
import com.example.appderecetario.domain.usecase.AddFavoriteRecipeUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddFavoriteRecipeUseCaseTest {
    private val repository: RecipeRepository = mockk(relaxed = true)
    private lateinit var addUseCase: AddFavoriteRecipeUseCase

    @Before
    fun setUp() {
        addUseCase = AddFavoriteRecipeUseCase(repository)
    }

    @Test
    fun `addFavorite delega al repositorio`() = runTest {
        val recipe = Recipe(1, "Tacos", "", "desc", emptyList(), emptyList(), false)

        addUseCase(recipe)

        coVerify { repository.addFavorite(recipe) }
    }
}