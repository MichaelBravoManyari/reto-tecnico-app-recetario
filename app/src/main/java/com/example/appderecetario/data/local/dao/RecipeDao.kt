package com.example.appderecetario.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appderecetario.data.local.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(recipe: RecipeEntity)

    @Delete
    suspend fun deleteFavorite(recipe: RecipeEntity)

    @Query("SELECT * FROM favorites")
    fun getFavorites(): Flow<List<RecipeEntity>>
}