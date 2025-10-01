package com.example.appderecetario.data.remote.dto

data class RecipeDto(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val ingredients: List<String>,
    val steps: List<String>
)