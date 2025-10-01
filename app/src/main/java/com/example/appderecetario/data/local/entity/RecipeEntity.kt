package com.example.appderecetario.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class RecipeEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val image: String,
    val description: String
)
