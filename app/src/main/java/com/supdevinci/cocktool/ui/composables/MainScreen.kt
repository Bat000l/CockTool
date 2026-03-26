package com.supdevinci.cocktool.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.supdevinci.cocktool.R
import com.supdevinci.cocktool.ui.state.ApiState
import com.supdevinci.cocktool.viewmodel.ApiCocktailViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: ApiCocktailViewModel = viewModel()) {

    val state = viewModel.state.collectAsStateWithLifecycle()
    var searchText by remember { mutableStateOf("") }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {

            ModalDrawerSheet {
                Text("CockTool", modifier = Modifier.padding(16.dp))

                NavigationDrawerItem(
                    label = { Text("Aléatoire") },
                    selected = false,
                    onClick = {
                        viewModel.getRandomCocktail()
                        scope.launch { drawerState.close() }
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Sélection") },
                    selected = false,
                    onClick = {
                        viewModel.getRandomSelection()
                        scope.launch { drawerState.close() }
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Cocktails") },
                    selected = false,
                    onClick = {
                        viewModel.getCocktailsByLetter("a")
                        scope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {

                            // 🖼️ LOGO
                            Image(
                                painter = painterResource(id = R.drawable.logo_cocktool),
                                contentDescription = "Logo",
                                modifier = Modifier.size(50.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text("CockTool")
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = { Text("Rechercher un cocktail") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            val query = searchText.trim()
                            if (query.isNotEmpty()) {
                                viewModel.searchCocktail(query)
                            }
                        }
                    ),
                    trailingIcon = {
                        IconButton(onClick = {
                            val query = searchText.trim()
                            if (query.isNotEmpty()) {
                                viewModel.searchCocktail(query)
                            }
                        }) {
                            Icon(Icons.Default.Search, contentDescription = "Rechercher")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )


                Spacer(modifier = Modifier.height(16.dp))

                // 📊 AFFICHAGE
                when (val result = state.value) {

                    is ApiState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is ApiState.Success -> {
                        LazyColumn {
                            items(result.drinks) { drink ->
                                Text(drink.strDrink)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }

                    is ApiState.Error -> {
                        Text(result.message, color = MaterialTheme.colorScheme.error)
                    }

                }
            }
        }
    }
}