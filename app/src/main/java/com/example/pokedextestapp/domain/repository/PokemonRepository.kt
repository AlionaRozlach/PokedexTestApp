package com.example.pokedextestapp.domain.repository

import com.example.pokedextestapp.data.remote.model.PokemonDetailDto
import com.example.pokedextestapp.data.remote.model.PokemonDto
import com.example.pokedextestapp.data.remote.responses.Pokemon
import com.example.pokedextestapp.data.remote.responses.PokemonList
import entity.common.NamedApiResources

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): PokemonList
    suspend fun getPokemonDetails(name: String): entity.pokemon.Pokemon
}