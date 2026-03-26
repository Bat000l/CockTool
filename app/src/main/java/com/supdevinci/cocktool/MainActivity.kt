package com.supdevinci.cocktool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.supdevinci.cocktool.ui.composables.CocktailItem
import com.supdevinci.cocktool.ui.composables.CocktailScreen
import com.supdevinci.cocktool.ui.composables.MainScreen
import com.supdevinci.cocktool.ui.theme.CockToolTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CockToolTheme {
               MainScreen()
            }
        }
    }
}
