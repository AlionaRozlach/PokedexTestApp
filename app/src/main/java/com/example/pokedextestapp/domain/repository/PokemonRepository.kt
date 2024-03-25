package com.example.pokedextestapp.domain.repository

import com.example.pokedextestapp.data.remote.responses.PokemonList

/**
 * Interface representing a client
 * for interacting with an API using the PokeAPI-Kotlin wrapper.
 * This wrapper didn't return all data correctly,
 * that's why Retrofit is still used in the project
 * for working with the API.
 */
interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): PokemonList
    suspend fun getPokemonDetails(name: String): entity.pokemon.Pokemon
}