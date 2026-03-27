package com.supdevinci.cocktool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.supdevinci.cocktool.model.Drink
import com.supdevinci.cocktool.navigation.CocktailNavHost
import com.supdevinci.cocktool.navigation.Routes
import com.supdevinci.cocktool.ui.theme.CockToolTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CockToolTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                var selectedDrink by remember { mutableStateOf<Drink?>(null) }

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {

                        ModalDrawerSheet {
                            NavigationDrawerItem(
                                label = { Text("CockTool", modifier = Modifier.padding(16.dp)) },
                                selected = false,
                                onClick = {
                                    navController.navigate(Routes.MAIN)
                                    scope.launch { drawerState.close() }
                                }
                            )

                            NavigationDrawerItem(
                                label = { Text("Random") },
                                selected = false,
                                onClick = {
                                    navController.navigate(Routes.RANDOM)
                                    scope.launch { drawerState.close() }
                                }
                            )

                            NavigationDrawerItem(
                                label = { Text("My Cocktails") },
                                selected = false,
                                onClick = {
                                    navController.navigate(Routes.MYCOCKTAILS)
                                    scope.launch { drawerState.close() }
                                }
                            )

                            NavigationDrawerItem(
                                label = { Text("My Favorites") },
                                selected = false,
                                onClick = {
                                    navController.navigate(Routes.MYFAVORITES)
                                    scope.launch { drawerState.close() }
                                }
                            )


                        }
                    }
                ) {


                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route


                    if (currentRoute != Routes.SPLASH) {
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    title = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {

                                            Image(
                                                painter = painterResource(id = R.drawable.logo_cocktool),
                                                contentDescription = "Logo",
                                                modifier = Modifier.size(50.dp)
                                            )

                                            Spacer(modifier = Modifier.width(8.dp))

                                            Text("CockTool")
                                        }
                                    },
                                    actions = {
                                        IconButton(onClick = {
                                            scope.launch { drawerState.open() }
                                        }) {
                                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                                        }
                                    }
                                )
                            }
                        ) { padding ->
                            Box(modifier = Modifier.padding(padding)) {
                                CocktailNavHost(
                                    navController = navController,
                                    selectedDrink = selectedDrink,
                                    onDrinkSelected = { selectedDrink = it },
                                    onBack = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }
                    } else {
                        // Sur SPLASH, afficher juste le NavHost sans TopBar
                        CocktailNavHost(
                            navController = navController,
                            selectedDrink = selectedDrink,
                            onDrinkSelected = { selectedDrink = it },
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
