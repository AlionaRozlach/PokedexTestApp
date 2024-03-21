package com.example.pokedextestapp.presentation.pokemons_list

import com.example.pokedextestapp.domain.model.PokemonModel

data class PokemonsListState (
    val isLoading: Boolean = false,
    val items: List<PokemonModel> = emptyList(),
    val error: String = "",
    val endReached: Boolean = false
)