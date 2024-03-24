package com.example.pokedextestapp.di

import com.example.pokedextestapp.data.repository.PokemonRetrofitRepositoryImpl
import com.example.pokedextestapp.domain.repository.PokemonRetrofitRepository
import com.example.pokedextestapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun providePokemonRepository(
        api: PokemonRetrofitRepository
    ) = PokemonRetrofitRepositoryImpl(api)

    @Singleton
    @Provides
    fun providePokeApi(): PokemonRetrofitRepository {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokemonRetrofitRepository::class.java)
    }
}