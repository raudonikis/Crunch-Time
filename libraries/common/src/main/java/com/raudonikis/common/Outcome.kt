package com.raudonikis.common

/**
 * Used to keep track of the result's status and value, and handle success/error
 */
sealed class Outcome<out T> {

    data class Success<out T>(val data: T) : Outcome<T>()
    object Empty : Outcome<Nothing>()
    object Failure : Outcome<Nothing>()
    object Loading : Outcome<Nothing>()
//    data class Failure(val error: ErrorCode, val message: String?) : Outcome<Nothing>()
//    data class Loading(val update: ProgressUpdate?) : Outcome<Nothing>()

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

    inline fun onFailure(f: (/*errorCode: ErrorCode*/) -> Unit): Outcome<T> {
        if (this is Failure) {
            f()
        }
        return this
    }

    inline fun <C> map(transform: (T) -> C): Outcome<C> {
        return when (this) {
            is Success -> success(transform(data))
            is Failure -> failure()
            is Empty -> empty()
            is Loading -> loading()
        }
    }

    companion object {
        fun <T> success(data: T) = Success(data)
        fun empty() = Empty
        fun failure() = Failure
        fun loading() = Loading
    }
}
