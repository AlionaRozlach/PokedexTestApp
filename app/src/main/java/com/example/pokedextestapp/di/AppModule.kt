package com.example.pokedextestapp.di

import com.example.pokedextestapp.data.repository.PokemonRepositoryImpl
import com.example.pokedextestapp.domain.repository.PokemonRepository
import com.example.pokedextestapp.domain.use_case.get_pokemons.GetPokemonsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePokemonRepository(): PokemonRepository {
        return PokemonRepositoryImpl()
    }

    @Provides
    fun provideGetItemUseCase(repository: PokemonRepository): GetPokemonsUseCase {
        return GetPokemonsUseCase(repository)
    }
}