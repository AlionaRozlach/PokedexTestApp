package com.example.pokedextestapp.domain.model

data class PokemonDetailModel(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val baseExep: Number,
    val formCounts: Int,
    val species: String,
    val types: List<String>,
    val description: String
)