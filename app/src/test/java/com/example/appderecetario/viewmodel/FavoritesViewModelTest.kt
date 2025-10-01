package com.example.appderecetario.viewmodel

import app.cash.turbine.test
import com.example.appderecetario.domain.model.Recipe
import com.example.appderecetario.domain.usecase.GetFavoriteRecipesUseCase
import com.example.appderecetario.domain.usecase.RemoveFavoriteRecipeUseCase
import com.example.appderecetario.presentation.recipes.favorites.FavoritesViewModel
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
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
class FavoritesViewModelTest {
    private val getFavoriteRecipesUseCase: GetFavoriteRecipesUseCase = mockk()
    private val removeFavoriteRecipeUseCase: RemoveFavoriteRecipeUseCase = mockk(relaxed = true)

    private lateinit var viewModel: FavoritesViewModel

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
    fun `cuando el flujo emite recetas, favoriteRecipes refleja la lista`() = runTest {
        val recipe1 = Recipe(1, "Tacos", "", "desc", listOf(), listOf(), false)
        val recipe2 = Recipe(2, "Pizza", "", "desc", listOf(), listOf(), true)

        val recipesFlow = flowOf(listOf(recipe1, recipe2))
        every { getFavoriteRecipesUseCase() } returns recipesFlow

        viewModel = FavoritesViewModel(getFavoriteRecipesUseCase, removeFavoriteRecipeUseCase)

        viewModel.favoriteRecipes.test {
            awaitItem()
            val favorites = awaitItem()
            assertEquals(2, favorites.size)
            assertEquals("Tacos", favorites[0].name)
            assertEquals("Pizza", favorites[1].name)
        }
    }

    @Test
    fun `cuando se elimina una receta, se invoca removeFavoriteRecipeUseCase`() = runTest {
        val recipe = Recipe(3, "Burrito", "", "desc", listOf(), listOf(), true)
        every { getFavoriteRecipesUseCase() } returns flowOf(listOf(recipe))

        viewModel = FavoritesViewModel(getFavoriteRecipesUseCase, removeFavoriteRecipeUseCase)

        viewModel.removeFavorite(recipe)
        advanceUntilIdle()

        coVerify { removeFavoriteRecipeUseCase(recipe) }
    }

    @Test
    fun `cuando no hay favoritos, favoriteRecipes esta vacio`() = runTest {
        every { getFavoriteRecipesUseCase() } returns flowOf(emptyList())

        viewModel = FavoritesViewModel(getFavoriteRecipesUseCase, removeFavoriteRecipeUseCase)
        advanceUntilIdle()

        assertTrue(viewModel.favoriteRecipes.value.isEmpty())
    }
}