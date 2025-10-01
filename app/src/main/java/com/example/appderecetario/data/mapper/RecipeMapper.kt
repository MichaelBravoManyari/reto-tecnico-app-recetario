package com.example.appderecetario.data.mapper

import com.example.appderecetario.data.remote.dto.RecipeDto
import com.example.appderecetario.domain.model.Recipe


fun RecipeDto.toDomain(): Recipe {
    return Recipe(
        id = id,
        name = name,
        image = image,
        description = description,
        ingredients = ingredients,
        steps = steps,
        isFavorite = false
    )
}