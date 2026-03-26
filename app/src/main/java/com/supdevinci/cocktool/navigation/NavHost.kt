package com.supdevinci.cocktool.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.supdevinci.cocktool.ui.composables.MainScreen
import com.supdevinci.cocktool.ui.composables.MyCocktailsScreen
import com.supdevinci.cocktool.ui.composables.RandomCocktailScreen
import com.supdevinci.cocktool.ui.composables.SingleCocktailScreen
import com.supdevinci.cocktool.model.Drink
import com.supdevinci.cocktool.ui.composables.SplashScreen

object Routes {
    const val MAIN = "main"
    const val MYCOCKTAILS = "mycocktails"
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

        // 🎬 SPLASH
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

        // ❤️ LOCAL (ROOM)
        composable(Routes.MYCOCKTAILS) {
            MyCocktailsScreen(
                onCocktailClick = { name, instructions ->
                    onDrinkSelected(buildLocalDrink(name, instructions))
                    navController.navigate(Routes.DETAIL)
                }
            )
        }

        // 🎲 RANDOM
        composable(Routes.RANDOM) {
            RandomCocktailScreen()
        }

        // 📄 DETAIL
        composable(Routes.DETAIL) {
            selectedDrink?.let {
                SingleCocktailScreen(
                    drink = it,
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

