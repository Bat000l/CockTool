package com.supdevinci.cocktool.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.supdevinci.cocktool.data.local.entities.CocktailEntity
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CocktailItem(
    cocktail: CocktailEntity,
    imageUrl: String? = null,
    onFavorite: () -> Unit,
    onArchive: () -> Unit,
    onClick: () -> Unit
) {

    val formatter = remember {
        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    }

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = MaterialTheme.shapes.extraLarge,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {

        Column {

            // 🖼️ IMAGE
            AsyncImage(
                model = imageUrl ?: "https://via.placeholder.com/300",
                contentDescription = cocktail.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )

            Column(modifier = Modifier.padding(12.dp)) {

                // 🏷️ NOM
                Text(
                    text = cocktail.name,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(4.dp))

                // 📖 DESCRIPTION
                Text(
                    text = cocktail.instructions,
                    maxLines = 2,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(6.dp))

                // 📅 DATE
                Text(
                    text = "Créé le : ${formatter.format(cocktail.createdAt)}",
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 🔘 ACTIONS
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    // ⭐ FAVORI
                    IconButton(onClick = onFavorite) {
                        Icon(
                            imageVector = if (cocktail.isFavorite)
                                Icons.Default.Favorite
                            else
                                Icons.Default.FavoriteBorder,
                            contentDescription = "Favori",
                            tint = if (cocktail.isFavorite)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // 🗑️ ARCHIVE
                    IconButton(onClick = onArchive) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Archiver",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}