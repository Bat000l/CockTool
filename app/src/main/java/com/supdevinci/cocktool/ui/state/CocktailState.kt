package com.supdevinci.cocktool.ui.state

import com.supdevinci.cocktool.data.local.entities.CocktailEntity

sealed interface CocktailState {

    object Loading : CocktailState

    object Empty : CocktailState

    data class Success(
        val cocktails: List<CocktailEntity>
    ) : CocktailState

    data class Error(
        val message: String
    ) : CocktailState
}