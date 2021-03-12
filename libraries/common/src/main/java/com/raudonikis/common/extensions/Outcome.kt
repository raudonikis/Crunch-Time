package com.raudonikis.common.extensions

/**
 * Used to keep track of the result's status and value, and handle success/error
 */
sealed class Outcome<out T> {

    data class Success<out T>(val data: T) : Outcome<T>()
    object Empty : Outcome<Nothing>()
    object Failure : Outcome<Nothing>()
//    data class Failure(val error: ErrorCode, val message: String?) : Outcome<Nothing>()
//    data class Loading(val update: ProgressUpdate?) : Outcome<Nothing>()

    inline fun onSuccess(f: (data: T) -> Unit): Outcome<T> {
        return when (this) {
            is Success -> {
                f(data)
                this
            }
            else -> this
        }
    }

    inline fun onEmpty(f: () -> Unit): Outcome<T> {
        return when (this) {
            is Empty -> {
                f()
                this
            }
            else -> this
        }
    }

    inline fun onFailure(f: (/*errorCode: ErrorCode*/) -> Unit): Outcome<T> {
        return when (this) {
            is Failure -> {
                f()
//                Timber.d("Error occurred, message -> $message")
                this
            }
            else -> this
        }
    }

    inline fun <C> map(transform: (T) -> C): Outcome<C> {
        return when (this) {
            is Success -> success(transform(data))
            is Failure -> failure()
            is Empty -> empty()
        }
    }

    companion object {
        fun <T> success(data: T) = Success(data)
        fun empty() = Empty
        fun failure() = Failure
    }
}
