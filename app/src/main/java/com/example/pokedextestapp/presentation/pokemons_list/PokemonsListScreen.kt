package com.example.pokedextestapp.presentation.pokemons_list

import android.graphics.Paint.Style
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.pokedextestapp.R
import com.example.pokedextestapp.domain.model.PokemonModel
import com.example.pokedextestapp.presentation.destinations.PokemonDetailScreenDestination
import com.example.pokedextestapp.presentation.pokemon_info.PokemonDetailScreen
import com.example.pokedextestapp.presentation.pokemons_list.components.PokemonListCard
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(start = true)
fun PokemonsListScreen(
    navigator: DestinationsNavigator,
    viewModel: PokemonsListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        CustomBackgroundColumn()

        // Layer your content on top of the background
        Column(modifier = Modifier.fillMaxSize()) {

            Text(
                text = "Pokedex",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 20.dp)
            )

            Box(modifier = Modifier.padding(top = 40.dp)) {
                PokemonList(
                    onItemClick = { pokemon ->
                        navigator.navigate(
                            PokemonDetailScreenDestination(name = pokemon.pokemonName)
                        )
                    }
                )
            }

            // Error message if there's an error
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

            // Loading indicator
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }

}

@Composable
fun CustomBackgroundColumn() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Image in the right corner
        Image(
            painter = painterResource(id = R.drawable.pokeball), // Replace with your image resource
            contentDescription = "",
            modifier = Modifier
                .size(300.dp) // Adjust size as needed
                .align(Alignment.TopEnd)
                .offset(
                    90.dp,
                    (-70).dp
                ), // Aligns the image to the top right corner , // Adjust padding as needed
            contentScale = ContentScale.Crop // Adjust content scale as needed
        )
    }
}

@Composable
fun PokemonItem(
    pokemon: PokemonModel,
    onItemClick: (PokemonModel) -> Unit, viewModel: PokemonsListViewModel
) {
    PokemonListCard(
        remoteImageUrl = pokemon.imageUrl,
        pokemonNumber = pokemon.number,
        pokemonName = pokemon.pokemonName,
        url = pokemon.url,
        typeList = pokemon.type,viewModel = viewModel,
        onItemClick = onItemClick,

        )
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
    viewModel: PokemonsListViewModel = hiltViewModel(),
    onItemClick: (PokemonModel) -> Unit
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(state.items.size) { index ->
                PokemonItem(state.items[index], onItemClick, viewModel)
                // Load more pokemons if we're near the end of the list
                if (index == state.items.size - 1 && !state.isLoading && !state.endReached) {
                    onLoadMore()
                }
            }
        }
    }
}

