package com.example.pokedextestapp.presentation.pokemons_list

import com.example.pokedextestapp.domain.model.PokemonModel

/**
 * Represents the state of the pokemons list screen.
 *
 * @property isLoading Indicates whether data is currently being loaded.
 * @property items The list of Pokemons.
 * @property error The error message if an error occurred while fetching data.
 * @property endReached Indicates whether all Pokemons have been loaded.
 */
data class PokemonsListState(
    val isLoading: Boolean = false,
    val items: List<PokemonModel> = emptyList(),
    val error: String = "",
    val endReached: Boolean = false
)