package com.supdevinci.cocktool.ui.state

import com.supdevinci.cocktool.model.Drink

sealed interface ApiState {
    object Loading : ApiState
    data class Success(val drinks: List<Drink>) : ApiState
    data class Error(val message: String) : ApiState
}