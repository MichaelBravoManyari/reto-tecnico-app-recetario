package com.example.appderecetario.presentation.recipes.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appderecetario.domain.model.Recipe
import com.example.appderecetario.domain.usecase.GetFavoriteRecipesUseCase
import com.example.appderecetario.domain.usecase.GetRecipesUseCase
import com.example.appderecetario.domain.usecase.ToggleFavoriteRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase,
    getFavoriteRecipesUseCase: GetFavoriteRecipesUseCase,
    private val toggleFavoriteRecipeUseCase: ToggleFavoriteRecipeUseCase
) : ViewModel() {

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes.asStateFlow()

    private val _favorites = getFavoriteRecipesUseCase().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val uiState: StateFlow<List<Recipe>> = combine(_recipes, _favorites) { recipes, favorites ->
        recipes.map { recipe ->
            recipe.copy(isFavorite = favorites.any { it.id == recipe.id })
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        fetchRecipes()
    }

    private fun fetchRecipes() {
        viewModelScope.launch {
            try {
                _recipes.value = getRecipesUseCase()
            } catch (e: Exception) {
                Log.e("RecipeListVM", "Error cargando recetas: ${e.message}")
            }
        }
    }

    fun toggleFavorite(recipe: Recipe) {
        viewModelScope.launch {
            toggleFavoriteRecipeUseCase(recipe)
        }
    }
}
