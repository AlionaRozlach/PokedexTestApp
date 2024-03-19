package com.example.pokedextestapp.data.repository

import com.example.pokedextestapp.domain.repository.PokemonRepository
import entity.common.NamedApiResources
import entity.pokemon.Pokemon

class PokemonRepositoryImpl : PokemonRepository {
    override suspend fun getPokemonList(limit: Int, offset: Int): NamedApiResources<Pokemon> {
        return PokeApi.get<Pokemon>(limit = limit, offset = offset)
    }

    override suspend fun getPokemonDetails(id: Int): Pokemon {
        return PokeApi.get<Pokemon>(id = id)
    }
}