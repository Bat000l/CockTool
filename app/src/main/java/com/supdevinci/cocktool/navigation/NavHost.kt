package com.supdevinci.cocktool.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.supdevinci.cocktool.ui.composables.MainScreen
import com.supdevinci.cocktool.ui.composables.MyCocktailsScreen
import com.supdevinci.cocktool.ui.composables.MyFavoritesScreen
import com.supdevinci.cocktool.ui.composables.RandomCocktailScreen
import com.supdevinci.cocktool.ui.composables.SingleCocktailScreen
import com.supdevinci.cocktool.model.Drink
import com.supdevinci.cocktool.ui.composables.SplashScreen
import com.supdevinci.cocktool.viewmodel.FavoritesViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.supdevinci.cocktool.data.local.entities.CocktailEntity

object Routes {
    const val MAIN = "main"
    const val MYCOCKTAILS = "mycocktails"
    const val MYFAVORITES = "myfavorites"
    const val RANDOM = "random"
    const val DETAIL = "detail"
    const val SPLASH = "splash"
}

@Composable
fun CocktailNavHost(
    navController: NavHostController,
    selectedDrink: Drink?,
    onDrinkSelected: (Drink) -> Unit,
    onBack: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        composable(Routes.SPLASH) {
            SplashScreen(navController = navController)
        }
        composable(Routes.MAIN) {
            MainScreen(
                onCocktailClick = { drink ->
                    onDrinkSelected(drink)
                    navController.navigate(Routes.DETAIL)
                }
            )
        }

        composable(Routes.MYCOCKTAILS) {
            MyCocktailsScreen(
                onCocktailClick = { name, instructions ->
                    onDrinkSelected(buildLocalDrink(name, instructions))
                    navController.navigate(Routes.DETAIL)
                }
            )
        }

        composable(Routes.MYFAVORITES) {
            MyFavoritesScreen(
                onCocktailClick = { cocktail ->
                    onDrinkSelected(buildDrinkFromEntity(cocktail))
                    navController.navigate(Routes.DETAIL)
                }
            )
        }

        composable(Routes.RANDOM) {
            RandomCocktailScreen()
        }

        composable(Routes.DETAIL) {
            selectedDrink?.let { drink ->
                val favoritesViewModel: FavoritesViewModel = viewModel()
                val favoritesState by favoritesViewModel.state.collectAsStateWithLifecycle()
                
                // Vérifier si le cocktail actuel est déjà dans les favoris
                var isFavorite by remember(drink, favoritesState) {
                    mutableStateOf(
                        if (favoritesState is com.supdevinci.cocktool.ui.state.CocktailState.Success) {
                            (favoritesState as com.supdevinci.cocktool.ui.state.CocktailState.Success).cocktails.any { it.name == drink.strDrink && it.isFavorite }
                        } else {
                            false
                        }
                    )
                }

                SingleCocktailScreen(
                    drink = drink,
                    isFavorite = isFavorite,
                    ToggleFavorite = {
                        isFavorite = !isFavorite
                        if (isFavorite) {
                            favoritesViewModel.addFavorite(drink)
                        } else {
                            favoritesViewModel.removeFavoriteByName(drink.strDrink)
                        }
                    }
                )
            }
        }
    }
}

private fun buildLocalDrink(name: String, instructions: String): Drink {
    return Drink(
        idDrink = "local",
        strDrink = name,
        strDrinkAlternate = null,
        strTags = null,
        strVideo = null,
        strCategory = null,
        strIBA = null,
        strAlcoholic = null,
        strGlass = null,
        strInstructions = instructions,
        strInstructionsES = null,
        strInstructionsDE = null,
        strInstructionsFR = null,
        strInstructionsIT = null,
        strDrinkThumb = null,
        strIngredient1 = null,
        strIngredient2 = null,
        strIngredient3 = null,
        strIngredient4 = null,
        strIngredient5 = null,
        strIngredient6 = null,
        strIngredient7 = null,
        strIngredient8 = null,
        strIngredient9 = null,
        strIngredient10 = null,
        strIngredient11 = null,
        strIngredient12 = null,
        strIngredient13 = null,
        strIngredient14 = null,
        strIngredient15 = null,
        strMeasure1 = null,
        strMeasure2 = null,
        strMeasure3 = null,
        strMeasure4 = null,
        strMeasure5 = null,
        strMeasure6 = null,
        strMeasure7 = null,
        strMeasure8 = null,
        strMeasure9 = null,
        strMeasure10 = null,
        strMeasure11 = null,
        strMeasure12 = null,
        strMeasure13 = null,
        strMeasure14 = null,
        strMeasure15 = null,
        strImageSource = null,
        strImageAttribution = null,
        strCreativeCommonsConfirmed = null,
        dateModified = null
    )
}

private fun buildDrinkFromEntity(entity: CocktailEntity): Drink {
    return Drink(
        idDrink = entity.id.toString(),
        strDrink = entity.name,
        strDrinkAlternate = null,
        strTags = null,
        strVideo = null,
        strCategory = null,
        strIBA = null,
        strAlcoholic = null,
        strGlass = null,
        strInstructions = entity.instructions,
        strInstructionsES = null,
        strInstructionsDE = null,
        strInstructionsFR = null,
        strInstructionsIT = null,
        strDrinkThumb = entity.thumbUrl,
        strIngredient1 = entity.ingredient1,
        strIngredient2 = entity.ingredient2,
        strIngredient3 = entity.ingredient3,
        strIngredient4 = entity.ingredient4,
        strIngredient5 = entity.ingredient5,
        strIngredient6 = entity.ingredient6,
        strIngredient7 = entity.ingredient7,
        strIngredient8 = entity.ingredient8,
        strIngredient9 = entity.ingredient9,
        strIngredient10 = entity.ingredient10,
        strIngredient11 = entity.ingredient11,
        strIngredient12 = entity.ingredient12,
        strIngredient13 = entity.ingredient13,
        strIngredient14 = entity.ingredient14,
        strIngredient15 = entity.ingredient15,
        strMeasure1 = entity.measure1,
        strMeasure2 = entity.measure2,
        strMeasure3 = entity.measure3,
        strMeasure4 = entity.measure4,
        strMeasure5 = entity.measure5,
        strMeasure6 = entity.measure6,
        strMeasure7 = entity.measure7,
        strMeasure8 = entity.measure8,
        strMeasure9 = entity.measure9,
        strMeasure10 = entity.measure10,
        strMeasure11 = entity.measure11,
        strMeasure12 = entity.measure12,
        strMeasure13 = entity.measure13,
        strMeasure14 = entity.measure14,
        strMeasure15 = entity.measure15,
        strImageSource = null,
        strImageAttribution = null,
        strCreativeCommonsConfirmed = null,
        dateModified = null
    )
}
