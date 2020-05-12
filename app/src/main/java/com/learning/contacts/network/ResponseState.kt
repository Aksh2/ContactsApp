package com.learning.contacts.network

sealed class ResponseState<T> {
    class Loading<T> : ResponseState<T>()

    data class Success<T>(val data: T) : ResponseState<T>()

    data class Error<T>(val message: String) : ResponseState<T>()

    companion object {
        fun <T> loading() = Loading<T>()

        fun <T> success(data: T) = Success<T>(data)

        fun <T> error(message: String) = Error<T>(message)
    }
}