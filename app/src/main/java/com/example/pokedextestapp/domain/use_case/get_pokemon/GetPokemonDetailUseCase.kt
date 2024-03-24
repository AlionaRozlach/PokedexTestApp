package com.example.pokedextestapp.domain.use_case.get_pokemon

import androidx.compose.ui.text.capitalize
import com.example.pokedextestapp.domain.model.PokemonDetailModel
import com.example.pokedextestapp.domain.model.PokemonListModel
import com.example.pokedextestapp.domain.model.PokemonModel
import com.example.pokedextestapp.domain.repository.PokemonRepository
import com.example.pokedextestapp.domain.repository.PokemonRetrofitRepository
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

class GetPokemonDetailUseCase @Inject constructor(
    private val retrofitRepository: PokemonRetrofitRepository
) {
    operator fun invoke(name: String): Flow<Resource<PokemonDetailModel>> = flow {
        try {
            val pokemonSpecies = retrofitRepository.getPokemonSpecies(name)
            val pokemonEntity = retrofitRepository.getPokemonInfo(name)
            val flavorTextEntriesInEnglish = pokemonSpecies.flavor_text_entries[7]

            val pokemonDetailModel =
                PokemonDetailModel(
                    id = pokemonEntity.id.toInt(),
                    name = pokemonEntity.name.capitalize(),
                    height = pokemonEntity.height.toInt(),
                    weight = pokemonEntity.weight.toInt(),
                    baseExep = pokemonEntity.base_experience ?: 0,
                    formCounts = pokemonEntity.forms.size,
                    species = pokemonSpecies.egg_groups.joinToString(", ") { it.name.capitalize() },
                    types = pokemonEntity.types.map { it.type.name.capitalize()},
                    description = flavorTextEntriesInEnglish.flavor_text ?: "Description is not available."
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

