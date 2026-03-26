package com.supdevinci.cocktool.ui.composables


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.supdevinci.cocktool.model.Drink

@Composable
fun SingleCocktailScreen(
    drink: Drink,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        AsyncImage(
            model = drink.strDrinkThumb ?: "https://via.placeholder.com/300",
            contentDescription = drink.strDrink,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🍸 NOM
        Text(
            text = drink.strDrink ?: "Sans nom",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 📖 INSTRUCTIONS
        Text(
            text = drink.strInstructions ?: "Pas d'instructions",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🍹 INGREDIENTS (simple version)
        Text(
            text = "Ingrédients",
            style = MaterialTheme.typography.titleMedium
        )

        val ingredients = listOf(
            drink.strIngredient1,
            drink.strIngredient2,
            drink.strIngredient3,
            drink.strIngredient4,
            drink.strIngredient5
        ).filterNotNull()

        ingredients.forEach {
            Text("• $it")
        }
    }
}