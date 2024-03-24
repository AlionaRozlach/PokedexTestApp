package com.example.pokedextestapp.domain.repository

import com.example.pokedextestapp.data.remote.responses.Pokemon
import com.example.pokedextestapp.data.remote.responses.PokemonSpecies
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonRetrofitRepository {
    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String
    ): Pokemon
    @GET("pokemon-species/{name}")
    suspend fun getPokemonSpecies(
        @Path("name") name: String
    ): PokemonSpecies
}