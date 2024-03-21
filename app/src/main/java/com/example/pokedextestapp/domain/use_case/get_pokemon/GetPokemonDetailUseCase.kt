package com.example.pokedextestapp.domain.use_case.get_pokemon

import com.example.pokedextestapp.domain.model.PokemonDetailModel
import com.example.pokedextestapp.domain.model.PokemonListModel
import com.example.pokedextestapp.domain.model.PokemonModel
import com.example.pokedextestapp.domain.repository.PokemonRepository
import com.example.pokedextestapp.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetPokemonDetailUseCase@Inject constructor(private val repository: PokemonRepository) {
    operator fun invoke(name: String): Flow<Resource<PokemonDetailModel>> = flow {
        try {
            val pokemonEntity = repository.getPokemonDetails(name)

            val pokemonDetailModel = PokemonDetailModel(
                id = pokemonEntity.id.toInt(),
                name = pokemonEntity.name,
                height = pokemonEntity.height.toInt(),
                weight = pokemonEntity.weight.toInt(),
//                hp = getPokemonStatValue(pokemonEntity, "hp"),
//                attack = getPokemonStatValue(pokemonEntity, "attack"),
//                defense = getPokemonStatValue(pokemonEntity, "defense"),
//                speed = getPokemonStatValue(pokemonEntity, "speed"),
//                types = pokemonEntity.types.joinToString { it.type.name }
            )

            emit(Resource.Success(pokemonDetailModel))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't make API call. Check your internet connection."))
        }
    }

    // Helper function to get the value of a specific stat for a Pokemon
    private fun getPokemonStatValue(pokemon: entity.pokemon.Pokemon, statName: String): Int {
        return pokemon.stats.firstOrNull { it.stat.name == statName }?.baseStat?.toInt() ?: 0
    }
}

