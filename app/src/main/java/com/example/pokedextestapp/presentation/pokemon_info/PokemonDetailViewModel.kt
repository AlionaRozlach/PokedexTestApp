package com.example.pokedextestapp.presentation.pokemon_info

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedextestapp.domain.model.PokemonModel
import com.example.pokedextestapp.domain.use_case.get_pokemon.GetPokemonDetailUseCase
import com.example.pokedextestapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * ViewModel for the Pokemon Detail screen.
 * @param getPokemonDetailUseCase The use case responsible for fetching Pokemon details.
 * @param savedStateHandle The saved state handle for retrieving information from the previous screen.
 */
@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private var currentPage = 0
    private val _state = MutableStateFlow(PokemonDetailState())
    val state: StateFlow<PokemonDetailState> = _state.asStateFlow()

    private var pokemonList = mutableStateOf<List<PokemonModel>>(listOf())

    /**
     * Initializes the ViewModel.
     */
    init {
        val name = savedStateHandle.get<String>("name")
        if (name != null) {
            getPokemonDetail(name)
        }
    }

    /**
     * Fetches Pokemon details.
     * @param name The name of the Pokemon.
     */
    fun getPokemonDetail(name: String) {
        getPokemonDetailUseCase(
            name
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value =
                        PokemonDetailState(pokemon = result.data)
                }

                is Resource.Loading -> {
                    _state.value = PokemonDetailState(isLoading = true)
                }

                is Resource.Error -> {
                    _state.value =
                        PokemonDetailState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Converts weight from hectograms to kilograms.
     * @param weightInHectograms The weight in hectograms.
     * @return The weight in kilograms.
     */
    fun convertWeightToKilograms(weightInHectograms: Int): String {
        return (weightInHectograms.toDouble() / 10.0).toString()
    }

    /**
     * Converts height from decimeters to centimeters.
     * @param heightInDecimeters The height in decimeters.
     * @return The height in centimeters.
     */
    fun convertHeightToCentimeters(heightInDecimeters: Int): Int {
        return (heightInDecimeters * 10)
    }

    /**
     * Converts weight from hectograms to pounds.
     * @param weightInHectograms The weight in hectograms.
     * @return The weight in pounds.
     */
    fun convertWeightToPounds(weightInHectograms: Int): String {
        return String.format("%.1f", weightInHectograms.toDouble() * 0.220462)
    }

    /**
     * Formats height for display.
     * @param heightCm The height in centimeters.
     * @return The formatted height string.
     */
    fun formatHeight(heightCm: Int): String {
        val inchesTotal = heightCm.toDouble() * 0.393701
        val feet = (inchesTotal / 12).toInt()
        val remainingInches = inchesTotal % 12
        return String.format("%d'%.1f\" (%d cm)", feet, remainingInches, heightCm)
    }
}