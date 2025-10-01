package com.example.appderecetario.domain.model

data class Recipe(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val ingredients: List<String>,
    val steps: List<String>,
    val isFavorite: Boolean = false
)
