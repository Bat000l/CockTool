package com.supdevinci.cocktool.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supdevinci.cocktool.data.remote.RetrofitInstance
import com.supdevinci.cocktool.ui.state.ApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RandomCocktailViewModel : ViewModel(){
    private val _state = MutableStateFlow<ApiState>(ApiState.Loading)
    val state: StateFlow<ApiState> = _state

    fun getRandomCocktail() {
        viewModelScope.launch {
            _state.value = ApiState.Loading
            try {
                val response = RetrofitInstance.api.getRandomCocktail()
                _state.value = ApiState.Success(response.drinks ?: emptyList())
            } catch (e: Exception) {
                _state.value = ApiState.Error("Erreur réseau")
            }
        }
    }
}