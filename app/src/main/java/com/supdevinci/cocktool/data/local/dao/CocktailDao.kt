package com.supdevinci.cocktool.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.supdevinci.cocktool.data.local.entities.CocktailEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cocktail: CocktailEntity)

    @Update
    suspend fun update(cocktail: CocktailEntity)

    @Query("UPDATE my_cocktails SET deletedAt = :date WHERE id = :id")
    suspend fun softDelete(id: Int, date: Date)

    @Query("SELECT * FROM my_cocktails WHERE deletedAt IS NULL")
    fun getAllVisibleCocktails(): Flow<List<CocktailEntity>>
}