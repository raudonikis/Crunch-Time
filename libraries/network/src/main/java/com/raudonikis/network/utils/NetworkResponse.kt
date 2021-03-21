package com.raudonikis.network.utils

import com.raudonikis.common.extensions.Outcome
import com.squareup.moshi.Json
import timber.log.Timber

data class NetworkResponse<T>(
    /*@Json(name = "status")
    val status: Int,*/
    @Json(name = "success")
    //todo make it not nullable when backend is fixed
    val success: Boolean? = null,
    @Json(name = "data")
    val data: T? = null,
    @Json(name = "error")
    val error: NetworkErrorResponse? = null
) {

    fun isSuccess(): Boolean {
        return /*success != null && success && */data != null
    }

    fun isSuccessEmpty(): Boolean {
        return success != null && success && data == null
    }

    fun isFailure(): Boolean {
        return !isSuccess() && !isSuccessEmpty()
    }

    inline fun <C> map(transform: (T) -> C): NetworkResponse<C> {
        return if (data != null) {
            NetworkResponse(/*this.status, */success, transform(data), error)
        } else {
            NetworkResponse(/*this.status, */success, data, error)
        }
    }

    inline fun onSuccess(f: (data: T) -> Unit): NetworkResponse<T> {
        if (isSuccess() && data != null) {
            Timber.v("NetworkResponse: Success -> $data")
            f(data)
        } else if (isSuccessEmpty()) {
            Timber.w("NetworkResponse: Empty")
        } else {
            Timber.w("NetworkResponse: Failure -> $error")
            Timber.w("NetworkResponse: Failure -> $this")

        }
        return this
    }

    inline fun onEmpty(f: () -> Unit): NetworkResponse<T> {
        if (isSuccessEmpty()) {
            f()
        }
        return this
    }

    inline fun onFailure(f: () -> Unit): NetworkResponse<T> {
        if (isFailure()) {
            f()
        }
        return this
    }

    fun toOutcome(): Outcome<T> {
        this
            .onSuccess {
                return Outcome.success(it)
            }
            .onFailure {
                return Outcome.failure()
            }
            .onEmpty {
                return Outcome.empty()
            }
        return Outcome.failure()
    }
}