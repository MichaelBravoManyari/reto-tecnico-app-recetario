package com.example.appderecetario.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.appderecetario.domain.model.Recipe
import com.example.appderecetario.domain.usecase.GetRecipeByIdUseCase
import com.example.appderecetario.presentation.recipes.detail.RecipeDetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RecipeDetailViewModelTest {

    private val getRecipeByIdUseCase: GetRecipeByIdUseCase = mockk()
    private lateinit var viewModel: RecipeDetailViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `cuando receta existe, uiState pasa a Success`() = runTest {
        val recipe = Recipe(1, "Tacos", "", "desc", emptyList(), emptyList(), false)
        coEvery { getRecipeByIdUseCase(1) } returns recipe

        val savedStateHandle = SavedStateHandle(mapOf("recipeId" to 1))
        viewModel = RecipeDetailViewModel(getRecipeByIdUseCase, savedStateHandle)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is RecipeDetailViewModel.RecipeDetailUiState.Success)
        val success = state as RecipeDetailViewModel.RecipeDetailUiState.Success
        assertEquals("Tacos", success.recipe.name)
        assertFalse(success.recipe.isFavorite)
    }

    @Test
    fun `cuando receta no existe, uiState pasa a Error`() = runTest {
        coEvery { getRecipeByIdUseCase(99) } returns null

        val savedStateHandle = SavedStateHandle(mapOf("recipeId" to 99))
        viewModel = RecipeDetailViewModel(getRecipeByIdUseCase, savedStateHandle)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is RecipeDetailViewModel.RecipeDetailUiState.Error)
        val error = state as RecipeDetailViewModel.RecipeDetailUiState.Error
        assertEquals("Receta no encontrada", error.message)
    }

    @Test
    fun `cuando usecase lanza excepcion, uiState pasa a Error`() = runTest {
        coEvery { getRecipeByIdUseCase(5) } throws RuntimeException("Fallo en repo")

        val savedStateHandle = SavedStateHandle(mapOf("recipeId" to 5))
        viewModel = RecipeDetailViewModel(getRecipeByIdUseCase, savedStateHandle)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is RecipeDetailViewModel.RecipeDetailUiState.Error)
        val error = state as RecipeDetailViewModel.RecipeDetailUiState.Error
        assertEquals("Fallo en repo", error.message)
    }
}