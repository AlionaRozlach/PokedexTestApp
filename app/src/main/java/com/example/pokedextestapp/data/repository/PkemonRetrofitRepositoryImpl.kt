package com.example.pokedextestapp.data.repository

import com.example.pokedextestapp.data.remote.responses.Pokemon
import com.example.pokedextestapp.domain.repository.PokemonRetrofitRepository
import javax.inject.Inject

class PokemonRetrofitRepositoryImpl @Inject constructor(
    private val api: PokemonRetrofitRepository
) {
     suspend fun getPokemonInfo(name: String): Pokemon {
         return api.getPokemonInfo(name)
    }
    suspend fun getPokemonSpecies(name: String): com.example.pokedextestapp.data.remote.responses.PokemonSpecies {
         return api.getPokemonSpecies(name)
    }
}