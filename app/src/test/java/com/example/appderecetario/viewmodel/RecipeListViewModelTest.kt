package com.example.appderecetario.viewmodel

import com.example.appderecetario.domain.model.Recipe
import com.example.appderecetario.domain.usecase.GetFavoriteRecipesUseCase
import com.example.appderecetario.domain.usecase.GetRecipesUseCase
import com.example.appderecetario.domain.usecase.ToggleFavoriteRecipeUseCase
import com.example.appderecetario.presentation.recipes.list.RecipeListViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RecipeListViewModelTest {

    private val getRecipesUseCase: GetRecipesUseCase = mockk()
    private val getFavoriteRecipesUseCase: GetFavoriteRecipesUseCase = mockk()
    private val toggleFavoriteRecipeUseCase: ToggleFavoriteRecipeUseCase = mockk(relaxed = true)

    private lateinit var viewModel: RecipeListViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val taco = Recipe(
        id = 1, name = "Tacos", image = "", description = "",
        ingredients = listOf(), steps = listOf(), isFavorite = false
    )
    private val pizza = Recipe(
        id = 2, name = "Pizza", image = "", description = "",
        ingredients = listOf(), steps = listOf(), isFavorite = false
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `cuando fetchRecipes obtiene recetas, recipes las contiene`() = runTest {
        coEvery { getRecipesUseCase() } returns listOf(taco, pizza)
        every { getFavoriteRecipesUseCase() } returns flowOf(emptyList())

        viewModel = RecipeListViewModel(
            getRecipesUseCase,
            getFavoriteRecipesUseCase,
            toggleFavoriteRecipeUseCase
        )
        advanceUntilIdle()

        assertEquals(2, viewModel.recipes.value.size)
        assertEquals("Tacos", viewModel.recipes.value[0].name)
    }

    @Test
    fun `cuando se llama a toggleFavorite, invoca ToggleFavoriteRecipeUseCase`() = runTest {
        coEvery { getRecipesUseCase() } returns listOf(taco)
        every { getFavoriteRecipesUseCase() } returns flowOf(emptyList())

        viewModel = RecipeListViewModel(
            getRecipesUseCase,
            getFavoriteRecipesUseCase,
            toggleFavoriteRecipeUseCase
        )
        advanceUntilIdle()

        viewModel.toggleFavorite(taco)
        advanceUntilIdle()

        coVerify { toggleFavoriteRecipeUseCase(taco) }
    }
}
