package com.supdevinci.cocktool.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.supdevinci.cocktool.data.local.CocktailDatabase
import com.supdevinci.cocktool.data.local.entities.CocktailEntity
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

    // ➕ AJOUTER UN COCKTAIL AUX FAVORIS
    fun addFavorite(name: String, instructions: String) {
        viewModelScope.launch {
            val cocktail = CocktailEntity(
                name = name,
                instructions = instructions,
                isFavorite = true,
                createdAt = Date()
            )
            dao.insert(cocktail)
        }
    }

    // 📋 OBTENIR TOUS LES FAVORIS (pour un composable qui aurait besoin)
    fun refreshFavorites() {
        observeFavorites()
    }
}