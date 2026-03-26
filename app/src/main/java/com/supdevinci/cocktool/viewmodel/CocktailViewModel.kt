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

class CocktailViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = CocktailDatabase
        .getDatabase(application)
        .cocktailDao()

    private val _state = MutableStateFlow<CocktailState>(CocktailState.Loading)
    val state: StateFlow<CocktailState> = _state

    init {
        observeCocktails()
    }

    // 👀 OBSERVER LA DB
    private fun observeCocktails() {
        viewModelScope.launch {
            dao.getAllVisibleCocktails().collect { cocktails ->
                if (cocktails.isEmpty()) {
                    _state.value = CocktailState.Empty
                } else {
                    _state.value = CocktailState.Success(cocktails)
                }
            }
        }
    }

    // ➕ AJOUTER
    fun addCocktail(name: String, instructions: String) {
        viewModelScope.launch {
            val cocktail = CocktailEntity(
                name = name,
                instructions = instructions,
                createdAt = Date()
            )
            dao.insert(cocktail)
        }
    }

    // ⭐ FAVORI
    fun toggleFavorite(cocktail: CocktailEntity) {
        viewModelScope.launch {
            val updated = cocktail.copy(
                isFavorite = !cocktail.isFavorite,
                updatedAt = Date()
            )
            dao.update(updated)
        }
    }

    // 🗑️ ARCHIVE
    fun archiveCocktail(id: Int) {
        viewModelScope.launch {
            dao.softDelete(id, Date())
        }
    }

}