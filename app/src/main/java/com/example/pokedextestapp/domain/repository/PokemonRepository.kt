package com.example.pokedextestapp.domain.repository

import com.example.pokedextestapp.data.remote.responses.PokemonList

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): PokemonList
    suspend fun getPokemonDetails(name: String): entity.pokemon.Pokemon
}