package com.example.appderecetario.data.remote.api

import com.example.appderecetario.data.remote.dto.RecipeDto
import retrofit2.http.GET

interface RecipeApi {
    @GET("recipes.json")
    suspend fun getRecipes(): List<RecipeDto>
}