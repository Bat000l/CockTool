package com.supdevinci.cocktool.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.supdevinci.cocktool.data.local.entities.CocktailEntity
import com.supdevinci.cocktool.model.Drink
import com.supdevinci.cocktool.ui.state.ApiState
import com.supdevinci.cocktool.viewmodel.ApiCocktailViewModel
import java.util.Date

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: ApiCocktailViewModel = viewModel(),
    onCocktailClick: (Drink) -> Unit

) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text("Search for a cocktail") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    val query = searchText.trim()
                    if (query.isNotEmpty()) viewModel.searchCocktail(query)
                }
            ),
            trailingIcon = {
                IconButton(onClick = {
                    val query = searchText.trim()
                    if (query.isNotEmpty()) viewModel.searchCocktail(query)
                }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (val result = state.value) {
            is ApiState.Loading -> CircularProgressIndicator()
            is ApiState.Success -> {
                LazyColumn {
                    items(result.drinks) { drink ->
                        CocktailItem(
                            cocktail = CocktailEntity(
                                name = drink.strDrink ?: "Sans nom",
                                instructions = drink.strInstructions ?: "",
                                createdAt = Date()
                            ),
                            imageUrl = drink.strDrinkThumb,
                            onFavorite = {},
                            onArchive = {},
                            onClick = {}
                        )
                    }
                }
            }
            is ApiState.Error -> Text(result.message, color = MaterialTheme.colorScheme.error)
        }
    }
}