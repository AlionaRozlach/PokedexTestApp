package com.example.pokedextestapp.data.remote.model

import com.example.pokedextestapp.domain.model.PokemonDetailModel

data class PokemonDetailDto(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val speed: Int,
    val types: String
) {
    fun toPokemonDetailModel(): PokemonDetailModel {
        return PokemonDetailModel(
            id = id,
            name = name,
            height = height,
            weight = weight,
            hp = hp,
            attack = attack,
            defense = defense,
            speed = speed,
            types = types
        )
    }
}