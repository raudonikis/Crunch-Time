package com.raudonikis.common

/**
 * Used to keep track of the result's status and value, and handle success/error
 */
sealed class Outcome<out T> {

    data class Success<out T>(val data: T) : Outcome<T>()
    object Empty : Outcome<Nothing>()
    data class Failure(val message: String?) : Outcome<Nothing>()
    object Loading : Outcome<Nothing>()

    inline fun onSuccess(f: (data: T) -> Unit): Outcome<T> {
        if (this is Success) {
            f(data)
        }
        return this
    }

    inline fun onEmpty(f: () -> Unit): Outcome<T> {
        if (this is Empty) {
            f()
        }
        return this
    }

    inline fun onLoading(f: () -> Unit): Outcome<T> {
        if (this is Loading) {
            f()
        }
        return this
    }

    inline fun onFailure(f: (message: String?) -> Unit): Outcome<T> {
        if (this is Failure) {
            f(message)
        }
        return this
    }

    inline fun <C> map(transform: (T) -> C): Outcome<C> {
        return when (this) {
            is Success -> success(transform(data))
            is Failure -> failure(message)
            is Empty -> empty()
            is Loading -> loading()
        }
    }

    companion object {
        fun <T> success(data: T) = Success(data)
        fun empty() = Empty
        fun failure() = Failure(null)
        fun failure(message: String?) = Failure(message)
        fun loading() = Loading
    }
}
