package com.example.pokedextestapp.presentation.pokemons_list

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.pokedextestapp.domain.model.PokemonModel
import com.example.pokedextestapp.domain.use_case.get_pokemons.GetPokemonsUseCase
import com.example.pokedextestapp.util.Constants.PAGE_SIZE
import com.example.pokedextestapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PokemonsListViewModel @Inject constructor(private val getPokemonsUseCase: GetPokemonsUseCase) :
    ViewModel() {

    private var currentPage = 0
    private val _state = MutableStateFlow(PokemonsListState())
    val state: StateFlow<PokemonsListState> = _state.asStateFlow()

    private var pokemonList = mutableStateOf<List<PokemonModel>>(listOf())

    init {
        getPokemons()
    }

    fun getPokemons() {
        getPokemonsUseCase(PAGE_SIZE, currentPage * PAGE_SIZE).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value =
                        PokemonsListState(endReached = currentPage * PAGE_SIZE >= result.data!!.count)
                    pokemonList.value += result.data.pokemonModelList
                    _state.value = PokemonsListState(items = pokemonList.value)
                    currentPage++
                }

                is Resource.Loading -> {
                    _state.value = PokemonsListState(isLoading = true)
                }

                is Resource.Error -> {
                    _state.value =
                        PokemonsListState(error = result.message ?: "An unexpected error occurred")
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