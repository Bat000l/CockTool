package com.supdevinci.cocktool.ui.composables


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.supdevinci.cocktool.model.Drink

@Composable
fun SingleCocktailScreen(
    drink: Drink,
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    ToggleFavorite: () -> Unit,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {


        AsyncImage(
            model = drink.strDrinkThumb ?: "https://via.placeholder.com/300",
            contentDescription = drink.strDrink,
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(20.dp))


        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = drink.strDrink,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = ToggleFavorite,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite)
                        Icons.Default.Favorite
                    else
                        Icons.Default.FavoriteBorder,
                    contentDescription = "Favori",
                    tint = if (isFavorite)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "Préparation",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = drink.strInstructions ?: "Pas d'instructions",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Ingrédients",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        val ingredients = listOfNotNull(
            if(!drink.strIngredient1.isNullOrEmpty()) drink.strIngredient1 to drink.strMeasure1 else null,
            if(!drink.strIngredient2.isNullOrEmpty()) drink.strIngredient2 to drink.strMeasure2 else null,
            if(!drink.strIngredient3.isNullOrEmpty()) drink.strIngredient3 to drink.strMeasure3 else null,
            if(!drink.strIngredient4.isNullOrEmpty()) drink.strIngredient4 to drink.strMeasure4 else null,
            if(!drink.strIngredient5.isNullOrEmpty()) drink.strIngredient5 to drink.strMeasure5 else null,
            if(!drink.strIngredient6.isNullOrEmpty()) drink.strIngredient6 to drink.strMeasure6 else null,
            if(!drink.strIngredient7.isNullOrEmpty()) drink.strIngredient7 to drink.strMeasure7 else null,
            if(!drink.strIngredient8.isNullOrEmpty()) drink.strIngredient8 to drink.strMeasure8 else null,
            if(!drink.strIngredient9.isNullOrEmpty()) drink.strIngredient9 to drink.strMeasure9 else null,
            if(!drink.strIngredient10.isNullOrEmpty()) drink.strIngredient10 to drink.strMeasure10 else null,
            if(!drink.strIngredient11.isNullOrEmpty()) drink.strIngredient11 to drink.strMeasure11 else null,
            if(!drink.strIngredient12.isNullOrEmpty()) drink.strIngredient12 to drink.strMeasure12 else null,
            if(!drink.strIngredient13.isNullOrEmpty()) drink.strIngredient13 to drink.strMeasure13 else null,
            if(!drink.strIngredient14.isNullOrEmpty()) drink.strIngredient14 to drink.strMeasure14 else null,
            if(!drink.strIngredient15.isNullOrEmpty()) drink.strIngredient15 to drink.strMeasure15 else null,
        )

        ingredients.forEach { (ingredient, measure) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "•",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Column {
                    Text(
                        text = ingredient,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (measure != null) {
                        Text(
                            text = measure,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}