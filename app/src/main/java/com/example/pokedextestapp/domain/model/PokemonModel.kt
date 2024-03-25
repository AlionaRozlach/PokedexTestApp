package com.example.pokedextestapp.domain.model

import androidx.compose.ui.graphics.Color

/**
 * Pokemon Model.
 * @property pokemonName The name of the Pokemon.
 * @property url The URL of the Pokemon's information.
 * @property number The number of the Pokemon.
 * @property type The list of types of the Pokemon.
 * @property imageUrl The URL of the Pokemon's image.
 * @property dominantColor The dominant color of the Pokemon's image, can be null.
 */
data class PokemonModel(
    val pokemonName: String,
    val url: String,
    val number: Int,
    val type: List<String>,
    val imageUrl: String,
    val dominantColor: Color?
)