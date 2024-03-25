package com.example.pokedextestapp.presentation.pokemon_info

import com.example.pokedextestapp.domain.model.PokemonDetailModel

data class PokemonDetailState(
    val isLoading: Boolean = false,
    val pokemon: PokemonDetailModel? = null,
    val error: String = ""
)