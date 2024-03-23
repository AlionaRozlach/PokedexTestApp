package com.example.pokedextestapp.domain.model

data class PokemonModel(
    val pokemonName: String,
    val url: String,
    val number: Int,
    val type: List<String>,
    val imageUrl: String
)