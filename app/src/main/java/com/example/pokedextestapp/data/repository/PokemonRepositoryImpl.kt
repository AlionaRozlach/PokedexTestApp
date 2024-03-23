package com.example.pokedextestapp.data.repository

import com.example.pokedextestapp.data.remote.model.PokemonDetailDto
import com.example.pokedextestapp.data.remote.model.PokemonDto
import entity.pokemon.Pokemon
import com.example.pokedextestapp.data.remote.responses.PokemonList
import com.example.pokedextestapp.domain.repository.PokemonRepository
import com.example.pokedextestapp.data.remote.responses.Result
import entity.common.NamedApiResources
import entity.pokemon.PokemonSpecies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    override suspend fun getPokemonSpecies(name: String): entity.pokemon.PokemonSpecies {
        return withContext(Dispatchers.IO) {
            PokeApi.get<PokemonSpecies>(name)
        }
    }
}