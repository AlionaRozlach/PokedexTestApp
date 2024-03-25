package com.example.pokedextestapp.domain.use_case.get_pokemon

import com.example.pokedextestapp.domain.model.PokemonDetailModel
import com.example.pokedextestapp.domain.repository.PokemonRetrofitRepository
import com.example.pokedextestapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.util.Locale
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val retrofitRepository: PokemonRetrofitRepository
) {
    operator fun invoke(name: String): Flow<Resource<PokemonDetailModel>> = flow {
        try {
            emit(Resource.Loading(true))
            val pokemonSpecies = retrofitRepository.getPokemonSpecies(name)
            val pokemonEntity = retrofitRepository.getPokemonInfo(name)
            val flavorTextEntriesInEnglish = pokemonSpecies.flavor_text_entries[7]

            val pokemonDetailModel =
                PokemonDetailModel(
                    id = pokemonEntity.id,
                    name = pokemonEntity.name.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    },
                    height = pokemonEntity.height,
                    weight = pokemonEntity.weight,
                    baseExep = pokemonEntity.base_experience,
                    formCounts = pokemonEntity.forms.size,
                    species = pokemonSpecies.egg_groups.joinToString(", ") {
                        it.name.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.ROOT
                            ) else it.toString()
                        }
                    },
                    types = pokemonEntity.types.map { it.type.name.capitalize(Locale.ROOT) },
                    description = flavorTextEntriesInEnglish.flavor_text
                )
            emit(Resource.Success(pokemonDetailModel))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't make API call. Check your internet connection."))
        }
    }
}

