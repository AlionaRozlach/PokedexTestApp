package com.example.pokedextestapp.presentation.pokemon_info

import com.example.pokedextestapp.domain.model.PokemonDetailModel

/**
 * State class representing the state of Pokemon detail screen.
 * @property isLoading Indicates whether data is currently being loaded.
 * @property pokemon Contains the details of the Pokemon if loaded successfully, null otherwise.
 * @property error Contains the error message if an error occurred during data loading.
 */
data class PokemonDetailState(
    val isLoading: Boolean = false,
    val pokemon: PokemonDetailModel? = null,
    val error: String = ""
)