package com.supdevinci.cocktool.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.supdevinci.cocktool.data.local.entities.CocktailEntity
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun CocktailItem(
    cocktail: CocktailEntity,
    onFavorite: () -> Unit,
    onArchive: () -> Unit
) {

    val formatter = remember {
        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Text(
                text = cocktail.name,
                style = MaterialTheme.typography.titleMedium
            )

            Text(text = cocktail.instructions)

            Spacer(modifier = Modifier.height(4.dp))

            // 📅 Date de création
            Text(
                text = "Créé le : ${formatter.format(cocktail.createdAt)}",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row {

                // ⭐ FAVORI
                IconButton(onClick = onFavorite) {
                    Icon(
                        imageVector = if (cocktail.isFavorite)
                            Icons.Default.Favorite
                        else
                            Icons.Default.FavoriteBorder,
                        contentDescription = "Favori"
                    )
                }

                // 🗑️ ARCHIVE
                IconButton(onClick = onArchive) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Archiver"
                    )
                }
            }
        }
    }
}