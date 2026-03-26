package com.supdevinci.cocktool.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.supdevinci.cocktool.ui.composables.MainScreen
import com.supdevinci.cocktool.ui.composables.MyCocktailsScreen
import com.supdevinci.cocktool.ui.composables.SingleCocktailScreen
import com.supdevinci.cocktool.model.Drink

object Routes {
    const val MAIN = "main"
    const val MYCOCKTAILS = "mycocktails"
    const val DETAIL = "detail"
    const val ENCYCLOPEDIA = "encyclopedia"

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
        startDestination = Routes.MAIN
    ) {

        // 🍸 MAIN (API)
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
            MyCocktailsScreen()
        }

        // 📄 DETAIL
        composable(Routes.DETAIL) {
            selectedDrink?.let {
                SingleCocktailScreen(
                    drink = it,
                    onBack = onBack
                )
            }
        }
    }
}