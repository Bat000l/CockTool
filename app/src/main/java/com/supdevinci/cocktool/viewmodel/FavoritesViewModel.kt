package com.supdevinci.cocktool.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.supdevinci.cocktool.data.local.CocktailDatabase
import com.supdevinci.cocktool.data.local.entities.CocktailEntity
import com.supdevinci.cocktool.model.Drink
import com.supdevinci.cocktool.ui.state.CocktailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = CocktailDatabase
        .getDatabase(application)
        .cocktailDao()

    private val _state = MutableStateFlow<CocktailState>(CocktailState.Loading)
    val state: StateFlow<CocktailState> = _state

    init {
        observeFavorites()
    }

    // 👁️ OBSERVER LES FAVORIS
    private fun observeFavorites() {
        viewModelScope.launch {
            dao.getFavoriteCocktails().collect { cocktails ->
                if (cocktails.isEmpty()) {
                    _state.value = CocktailState.Empty
                } else {
                    _state.value = CocktailState.Success(cocktails)
                }
            }
        }
    }

    // ⭐ SUPPRIMER DES FAVORIS / TOGGLE FAVORI
    fun toggleFavorite(cocktail: CocktailEntity) {
        viewModelScope.launch {
            val updated = cocktail.copy(
                isFavorite = !cocktail.isFavorite,
                updatedAt = Date()
            )
            dao.update(updated)
        }
    }

    // 🗑️ ARCHIVER
    fun archiveCocktail(id: Int) {
        viewModelScope.launch {
            dao.softDelete(id, Date())
        }
    }

    fun removeFavoriteByName(name: String) {
        viewModelScope.launch {
            dao.removeFavoriteByName(name)
        }
    }


// ...existing code...
    // ➕ AJOUTER UN COCKTAIL AUX FAVORIS
    fun addFavorite(drink: Drink) {
        viewModelScope.launch {
            val cocktail = CocktailEntity(
                name = drink.strDrink,
                instructions = drink.strInstructions ?: "",
                thumbUrl = drink.strDrinkThumb,
                ingredient1 = drink.strIngredient1, measure1 = drink.strMeasure1,
                ingredient2 = drink.strIngredient2, measure2 = drink.strMeasure2,
                ingredient3 = drink.strIngredient3, measure3 = drink.strMeasure3,
                ingredient4 = drink.strIngredient4, measure4 = drink.strMeasure4,
                ingredient5 = drink.strIngredient5, measure5 = drink.strMeasure5,
                ingredient6 = drink.strIngredient6, measure6 = drink.strMeasure6,
                ingredient7 = drink.strIngredient7, measure7 = drink.strMeasure7,
                ingredient8 = drink.strIngredient8, measure8 = drink.strMeasure8,
                ingredient9 = drink.strIngredient9, measure9 = drink.strMeasure9,
                ingredient10 = drink.strIngredient10, measure10 = drink.strMeasure10,
                ingredient11 = drink.strIngredient11, measure11 = drink.strMeasure11,
                ingredient12 = drink.strIngredient12, measure12 = drink.strMeasure12,
                ingredient13 = drink.strIngredient13, measure13 = drink.strMeasure13,
                ingredient14 = drink.strIngredient14, measure14 = drink.strMeasure14,
                ingredient15 = drink.strIngredient15, measure15 = drink.strMeasure15,
                isFavorite = true,
                createdAt = Date()
            )
            dao.insert(cocktail)
        }
    }


}