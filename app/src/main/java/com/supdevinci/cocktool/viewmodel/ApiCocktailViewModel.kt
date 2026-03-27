package com.supdevinci.cocktool.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supdevinci.cocktool.data.remote.RetrofitInstance
import com.supdevinci.cocktool.ui.state.ApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ApiCocktailViewModel : ViewModel() {

    private val _state = MutableStateFlow<ApiState>(ApiState.Loading)
    val state: StateFlow<ApiState> = _state

    init {
        getRandomSelection()
    }

    fun getRandomSelection() {
        viewModelScope.launch {
            _state.value = ApiState.Loading
            try {
                val response = RetrofitInstance.api.getRandomSelection()
                _state.value = ApiState.Success(response.drinks ?: emptyList())
            } catch (e: Exception) {
                _state.value = ApiState.Error("Erreur réseau")
            }
        }
    }

    fun searchCocktail(name: String) {
        viewModelScope.launch {
            _state.value = ApiState.Loading
            try {
                val response = RetrofitInstance.api.searchCocktail(name)
                _state.value = ApiState.Success(response.drinks ?: emptyList())
            } catch (e: Exception) {
                _state.value = ApiState.Error("Erreur réseau")
            }
        }
    }

}