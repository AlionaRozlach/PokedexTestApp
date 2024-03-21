package com.example.pokedextestapp.data.remote.model

import com.example.pokedextestapp.domain.model.PokemonDetailModel
import com.example.pokedextestapp.domain.model.PokemonModel

data class PokemonDto(
    val pokemonName: String,
    val imageUrl: String,
    val id: Int,
    val type: String
) {
}
