package com.supdevinci.cocktool.service

import com.supdevinci.cocktool.model.CocktailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailService {

    @GET("search.php")
    suspend fun searchCocktail(
        @Query("s") name: String
    ): CocktailResponse

    @GET("random.php")
    suspend fun getRandomCocktail(): CocktailResponse


    @GET("randomselection.php")
    suspend fun getRandomSelection(): CocktailResponse
}