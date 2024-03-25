package com.example.pokedextestapp.domain.model

/**
 * Pokemon Detail Model.
 * @property id The ID of the Pokemon.
 * @property name The name of the Pokemon.
 * @property height The height of the Pokemon.
 * @property weight The weight of the Pokemon.
 * @property baseExep The base experience of the Pokemon.
 * @property formCounts The number of forms the Pokemon has.
 * @property species The species of the Pokemon.
 * @property types The list of types of the Pokemon.
 * @property description The description of the Pokemon.
 */
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