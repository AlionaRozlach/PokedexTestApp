package com.example.pokedextestapp.domain.use_case.get_pokemons

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

/**
 * Use case for retrieving a list of Pokemons.
 * @param repository The repository providing access to Pokemon data through wrapper PokeAPI - Kotlin.
 * @param limit The maximum number of Pokemons to retrieve.
 * @param offset The offset for pagination.
 * @return Flow<Resource<PokemonListModel>> A Flow emitting Resource wrapping PokemonListModel.
 */
class GetPokemonsUseCase @Inject constructor(private val repository: PokemonRepository) {
    operator fun invoke(limit: Int, offset: Int): Flow<Resource<PokemonListModel>> = flow {
        try {
            val pokemonsDto = repository.getPokemonList(limit, offset)
            val pokemonModels = mutableListOf<PokemonModel>()
            val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

            val pokemonDetailsDeferred = pokemonsDto.results.map { pokemon ->
                viewModelScope.async {
                    val pokemonDetail = repository.getPokemonDetails(pokemon.name)
                    val types = pokemonDetail.types.map { it.type.name }
                    val number = pokemonDetail.id.toInt()
                    PokemonModel(
                        pokemonName = pokemon.name,
                        url = pokemon.url,
                        number = number,
                        type = types,
                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png",
                        null
                    )
                }
            }

            val pokemonDetails = pokemonDetailsDeferred.awaitAll()
            pokemonModels.addAll(pokemonDetails)
            val pokemonListModel = PokemonListModel(pokemonModels, pokemonsDto.count)

            emit(Resource.Success(pokemonListModel))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't make API call. Check your internet connection."))
        }
    }
}

