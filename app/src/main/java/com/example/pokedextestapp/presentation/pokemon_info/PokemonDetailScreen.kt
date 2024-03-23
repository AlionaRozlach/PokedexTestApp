package com.example.pokedextestapp.presentation.pokemon_info

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokedextestapp.domain.model.PokemonDetailModel
import androidx.compose.material.Text
import com.example.pokedextestapp.presentation.pokemon_info.PokemonDetailViewModel
@Composable
@Destination
fun PokemonDetailScreen(
    name: String,
    viewModel: PokemonDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.isLoading) {
            // Show loading indicator
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            state.pokemon?.let {
                PokemonDetail(it)
                viewModel.getPokemonDetail(it.name)
            }
        }
    }
}

@Composable
fun PokemonDetail(pokemon: PokemonDetailModel) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Image(
//            painter = painterResource(id = R.drawable.pokemon_placeholder), // Replace with actual image resource
//            contentDescription = null,
//            modifier = Modifier
//                .size(200.dp)
//                .aspectRatio(1f),
//            contentScale = ContentScale.Fit
//        )


        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = pokemon.name,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Display other Pokemon details
        // You can customize the layout to display various details about the Pokemon
    }
}