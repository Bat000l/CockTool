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
import com.supdevinci.cocktool.ui.state.CocktailState
import com.supdevinci.cocktool.viewmodel.CocktailViewModel

@Composable
fun MyCocktailsScreen(
    modifier: Modifier = Modifier,
    viewModel: CocktailViewModel = viewModel(),
    onCocktailClick: (String, String) -> Unit = { _, _ -> }

) {

    val state = viewModel.state.collectAsStateWithLifecycle()

    var name by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }

    Column(
        modifier = modifier // 🔥 IMPORTANT
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // 🧾 FORMULAIRE
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Cocktail name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = instructions,
            onValueChange = { instructions = it },
            label = { Text("Recipe") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (name.isNotBlank() && instructions.isNotBlank()) {
                    viewModel.addCocktail(name, instructions)
                    name = ""
                    instructions = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 📊 ÉTAT
        when (val result = state.value) {

            is CocktailState.Loading -> {
                CircularProgressIndicator()
            }

            is CocktailState.Empty -> {
                Text("Aucun cocktail enregistré")
            }

            is CocktailState.Success -> {
                LazyColumn {
                    items(result.cocktails) { cocktail ->
                        CocktailItem(
                            cocktail = cocktail,
                            onFavorite = { viewModel.toggleFavorite(cocktail) },
                            onArchive = { viewModel.archiveCocktail(cocktail.id) },
                            onClick = {
                                onCocktailClick(cocktail.name, cocktail.instructions)
                            }
                        )
                    }
                }
            }

            is CocktailState.Error -> {
                Text(
                    result.message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}