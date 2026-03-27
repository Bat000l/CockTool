package com.supdevinci.cocktool.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.supdevinci.cocktool.data.local.entities.CocktailEntity
import com.supdevinci.cocktool.ui.state.CocktailState
import com.supdevinci.cocktool.viewmodel.FavoritesViewModel

@Composable
fun MyFavoritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = viewModel(),
    onCocktailClick: (CocktailEntity) -> Unit = {}
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 📋 TITRE
        Text(
            text = "Mes Favoris ⭐",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 📊 ÉTAT DE LA UI
        when (val result = state.value) {

            is CocktailState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(androidx.compose.ui.Alignment.CenterHorizontally)
                        .padding(16.dp)
                )
            }

            is CocktailState.Empty -> {
                Text(
                    text = "Aucun cocktail en favoris...",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 32.dp)
                )
            }

            is CocktailState.Success -> {
                LazyColumn {
                    items(result.cocktails) { cocktail ->
                        CocktailItem(
                            cocktail = cocktail,
                            onFavorite = { viewModel.toggleFavorite(cocktail) },
                            onArchive = { viewModel.archiveCocktail(cocktail.id) },
                            onClick = {
                                onCocktailClick(cocktail)
                            }
                        )
                    }
                }
            }

            is CocktailState.Error -> {
                Text(
                    text = result.message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

