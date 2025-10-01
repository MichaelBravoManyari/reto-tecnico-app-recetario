package com.example.appderecetario.presentation.recipes.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appderecetario.domain.model.Recipe
import com.example.appderecetario.domain.usecase.GetFavoriteRecipesUseCase
import com.example.appderecetario.domain.usecase.RemoveFavoriteRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    getFavoriteRecipesUseCase: GetFavoriteRecipesUseCase,
    private val removeFavoriteRecipeUseCase: RemoveFavoriteRecipeUseCase
) : ViewModel() {

    val favoriteRecipes: StateFlow<List<Recipe>> = getFavoriteRecipesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun removeFavorite(recipe: Recipe) {
        viewModelScope.launch {
            removeFavoriteRecipeUseCase(recipe)
        }
    }
}
