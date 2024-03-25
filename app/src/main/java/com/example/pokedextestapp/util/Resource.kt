package com.example.pokedextestapp.util

/**
 * A sealed class representing a resource that can be in one of three states: Success, Error, or Loading.
 *
 * @param T The type of data wrapped by the resource.
 * @property data The data wrapped by the resource.
 * @property message A message describing the resource, typically an error message.
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(val isLoading: Boolean = true) : Resource<T>(null)
}