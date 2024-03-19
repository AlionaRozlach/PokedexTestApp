package com.example.pokedextestapp.domain.repository

import entity.common.NamedApiResources
import entity.pokemon.Pokemon

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): NamedApiResources<Pokemon>
    suspend fun getPokemonDetails(id: Int): Pokemon
}