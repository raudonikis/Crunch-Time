package com.raudonikis.network.extensions

import com.haroldadmin.cnradapter.NetworkResponse
import timber.log.Timber

fun <S : Any, E : Any> NetworkResponse<S, E>.onSuccess(f: (data: S) -> Unit): NetworkResponse<S, E> {
    when (this) {
        is NetworkResponse.Success -> {
            Timber.v("NetworkResponse: Success -> ${this.body}")
            f(this.body)
        }
        is NetworkResponse.NetworkError -> {
            Timber.w("NetworkResponse: NetworkError -> ${this.error}")
        }
        is NetworkResponse.ServerError -> {
            Timber.w("NetworkResponse: ServerError -> ${this.code} -> ${this.body}")
        }
        is NetworkResponse.UnknownError -> {
            Timber.w("NetworkResponse: UnknownError -> ${this.code} -> ${this.error}")
        }
    }
    if (this is NetworkResponse.Success) {
        Timber.v("NetworkResponse: Success -> ${this.body}")
        f(this.body)
    }
    return this
}

fun <S : Any, E : Any> NetworkResponse<S, E>.onNetworkError(f: (exception: Exception) -> Unit): NetworkResponse<S, E> {
    if (this is NetworkResponse.NetworkError) {
        f(this.error)
    }
    return this
}

fun <S : Any, E : Any> NetworkResponse<S, E>.onServerError(f: (error: NetworkResponse.ServerError<E>) -> Unit): NetworkResponse<S, E> {
    if (this is NetworkResponse.ServerError) {
        f(this)
    }
    return this
}

fun <S : Any, E : Any> NetworkResponse<S, E>.onUnknownError(f: (error: NetworkResponse.UnknownError) -> Unit): NetworkResponse<S, E> {
    if (this is NetworkResponse.UnknownError) {
        f(this)
    }
    return this
}