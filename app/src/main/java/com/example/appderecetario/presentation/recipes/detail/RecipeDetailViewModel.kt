package com.example.appderecetario.presentation.recipes.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appderecetario.domain.model.Recipe
import com.example.appderecetario.domain.usecase.GetRecipeByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val recipeId: Int = checkNotNull(savedStateHandle["recipeId"]) as Int

    private val _uiState = MutableStateFlow<RecipeDetailUiState>(RecipeDetailUiState.Loading)
    val uiState: StateFlow<RecipeDetailUiState> = _uiState.asStateFlow()

    init {
        loadRecipe()
    }

    private fun loadRecipe() {
        viewModelScope.launch {
            try {
                _uiState.value = RecipeDetailUiState.Loading
                val recipe = getRecipeByIdUseCase(recipeId)
                if (recipe != null) {
                    _uiState.value = RecipeDetailUiState.Success(recipe.copy(isFavorite = false))
                } else {
                    _uiState.value = RecipeDetailUiState.Error("Receta no encontrada")
                }
            } catch (t: Throwable) {
                _uiState.value = RecipeDetailUiState.Error(t.message ?: "Error desconocido")
            }
        }
    }

    sealed class RecipeDetailUiState {
        object Loading : RecipeDetailUiState()
        data class Success(val recipe: Recipe) : RecipeDetailUiState()
        data class Error(val message: String) : RecipeDetailUiState()
    }
}
