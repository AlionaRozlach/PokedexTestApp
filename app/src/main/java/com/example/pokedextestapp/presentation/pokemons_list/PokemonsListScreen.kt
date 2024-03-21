package com.example.pokedextestapp.presentation.pokemons_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import com.example.pokedextestapp.domain.model.PokemonModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(start = true)
fun PokemonsListScreen(navigator: DestinationsNavigator,
                       viewModel: PokemonsListViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {

        PokemonList()
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}


@Composable
fun PokemonItem(pokemon: PokemonModel) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = pokemon.pokemonName,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Type: ${pokemon.type}",
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorView(error: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = error,
            color = Color.Red
        )
    }
}
@Composable
fun PokemonList(
    viewModel: PokemonsListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    // Trigger loading more pokemons when the end of the list is reached
    val onLoadMore = remember {
        {
            viewModel.getPokemons()
        }
    }

    // Show loading indicator while loading
    if (state.isLoading && state.items.isEmpty()) {
        LoadingIndicator()
    } else if (state.error.isNotEmpty()) {
        // Show error message if there's an error
        ErrorView(state.error)
    } else {
        // Show the list of pokemons in a grid with two columns
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(state.items.size) { index ->
                PokemonItem(state.items[index])
                // Load more pokemons if we're near the end of the list
                if (index == state.items.size - 1 && !state.isLoading && !state.endReached) {
                    onLoadMore()
                }
            }
        }
    }
}

