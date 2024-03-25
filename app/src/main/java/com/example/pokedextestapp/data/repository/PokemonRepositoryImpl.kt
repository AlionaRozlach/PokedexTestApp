package com.example.pokedextestapp.data.repository

import PokeApi
import com.example.pokedextestapp.data.remote.responses.PokemonList
import com.example.pokedextestapp.data.remote.responses.Result
import com.example.pokedextestapp.domain.repository.PokemonRepository
import entity.pokemon.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * This class implements methods for executing API requests
 * using the PokeApi-Kotlin wrapper.
 */
class PokemonRepositoryImpl : PokemonRepository {
    override suspend fun getPokemonList(limit: Int, offset: Int):PokemonList{
        return withContext(Dispatchers.IO) {
            val namedApiResources =
                PokeApi.get<entity.pokemon.Pokemon>(limit = limit, offset = offset)

             PokemonList(
                count = namedApiResources.count,
                next = namedApiResources.next
                    ?: "", // Provide a default empty string if next is null
                previous = namedApiResources.previous
                    ?: "", // Provide a default empty string if previous is null
                results = namedApiResources.results.map { result ->
                    Result(result.name, result.url ?: "")
                }
            )
        }
    }

    override suspend fun getPokemonDetails(name: String): entity.pokemon.Pokemon {
        return withContext(Dispatchers.IO) {
            PokeApi.get<Pokemon>(name)
        }
    }
}