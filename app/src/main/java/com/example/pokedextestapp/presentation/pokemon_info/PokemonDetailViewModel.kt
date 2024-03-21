package com.example.pokedextestapp.presentation.pokemon_info

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.pokedextestapp.domain.model.PokemonModel
import com.example.pokedextestapp.domain.use_case.get_pokemon.GetPokemonDetailUseCase
import com.example.pokedextestapp.domain.use_case.get_pokemons.GetPokemonsUseCase
import com.example.pokedextestapp.presentation.pokemons_list.PokemonsListState
import com.example.pokedextestapp.util.Constants
import com.example.pokedextestapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

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

    init {
        val name = savedStateHandle.get<String>("name")
        if (name != null) {
            getPokemonDetail(name)
        }
    }

    fun getPokemonDetail(name: String) {
        getPokemonDetailUseCase(
            name).onEach { result ->
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

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}