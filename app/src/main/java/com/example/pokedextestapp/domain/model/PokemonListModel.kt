package com.example.pokedextestapp.domain.model

/**
 * Pokemon List Model.
 * @property pokemonModelList The list of Pokemon models.
 * @property count The total count of Pokemon in the list.
 */
data class PokemonListModel(
    val pokemonModelList: List<PokemonModel>,
    val count: Int
)