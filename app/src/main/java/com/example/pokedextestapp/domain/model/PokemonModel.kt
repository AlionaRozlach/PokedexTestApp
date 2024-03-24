package com.example.pokedextestapp.domain.model

import androidx.compose.ui.graphics.Color

data class PokemonModel(
    val pokemonName: String,
    val url: String,
    val number: Int,
    val type: List<String>,
    val imageUrl: String,
    val dominantColor: Color?
)