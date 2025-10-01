package com.example.appderecetario.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appderecetario.data.local.dao.RecipeDao
import com.example.appderecetario.data.local.entity.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 1)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}