package com.raudonikis.network.utils

import com.raudonikis.common.Outcome
import com.squareup.moshi.Json
import timber.log.Timber

data class NetworkResponse<T>(
    /*@Json(name = "status")
    val status: Int,*/
    @Json(name = "success")
    val isSuccess: Boolean = false,
    @Json(name = "data")
    val data: T? = null,
    @Json(name = "error")
    val error: NetworkErrorResponse? = null
) {

    inline fun <C> map(transform: (T) -> C): NetworkResponse<C> {
        return if (data != null) {
            NetworkResponse(isSuccess, transform(data), error)
        } else {
            NetworkResponse(isSuccess, data, error)
        }
    }

    inline fun onSuccess(f: (data: T) -> Unit): NetworkResponse<T> {
        if (isSuccess && data != null) {
            Timber.v("NetworkResponse: Success -> $data")
            f(data)
        } else if (isSuccess && data == null) {
            Timber.w("NetworkResponse: Empty")
        } else {
            Timber.w("NetworkResponse: Failure -> $error")
            Timber.w("NetworkResponse: Failure -> $this")
        }
        return this
    }

    inline fun onEmpty(f: () -> Unit): NetworkResponse<T> {
        if (isSuccess && data == null) {
            f()
        }
        return this
    }

    inline fun onFailure(f: () -> Unit): NetworkResponse<T> {
        if (!isSuccess) {
            f()
        }
        return this
    }

    fun toOutcome(): Outcome<T> {
        return when {
            isSuccess && data != null -> Outcome.success(data)
            isSuccess && data == null -> Outcome.empty()
            else -> Outcome.failure(error?.message)
        }
    }
}