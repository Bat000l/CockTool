package com.supdevinci.cocktool.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.supdevinci.cocktool.ui.state.ApiState
import com.supdevinci.cocktool.ui.state.CocktailState
import com.supdevinci.cocktool.viewmodel.FavoritesViewModel
import com.supdevinci.cocktool.viewmodel.RandomCocktailViewModel


@Composable
fun RandomCocktailScreen(
    modifier: Modifier = Modifier,
    viewModel: RandomCocktailViewModel = viewModel(),
    favoritesViewModel: FavoritesViewModel = viewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val favoritesState by favoritesViewModel.state.collectAsStateWithLifecycle()

    // Charger un cocktail aléatoire au démarrage
    LaunchedEffect(Unit) {
        viewModel.getRandomCocktail()
    }

    Column(modifier = modifier.fillMaxSize()) {
        when (val apiState = state.value) {
            is ApiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is ApiState.Success -> {
                val drink = apiState.drinks.firstOrNull()
                if (drink != null) {
                    val isFav = if (favoritesState is CocktailState.Success) {
                        (favoritesState as CocktailState.Success).cocktails.any { it.name == drink.strDrink && it.isFavorite }
                    } else {
                        false
                    }

                    SingleCocktailScreen(
                        drink = drink,
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp),
                        isFavorite = isFav,
                        ToggleFavorite = {
                            if (isFav) {
                                favoritesViewModel.removeFavoriteByName(drink.strDrink)
                            } else {
                                favoritesViewModel.addFavorite(drink)
                            }
                        }
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Aucun cocktail trouvé")
                    }
                }
            }

            is ApiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Erreur: ${apiState.message}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.getRandomCocktail() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            Text("Random cocktail")
        }
    }
}
