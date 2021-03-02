package com.raudonikis.network.utils

import com.squareup.moshi.Json
import timber.log.Timber

data class NetworkResponse<T>(
    @Json(name = "status")
    val status: Int,
    @Json(name = "success")
    val isSuccess: Boolean,
    @Json(name = "data")
    val data: T?,
    @Json(name = "error")
    val error: NetworkErrorResponse?
) {

    inline fun <C> map(transform: (T) -> C): NetworkResponse<C> {
        return if (this.data != null) {
            NetworkResponse(this.status, this.isSuccess, transform(this.data), this.error)
        } else {
            NetworkResponse(this.status, this.isSuccess, this.data, this.error)
        }
    }

    inline fun onSuccess(f: (data: T) -> Unit): NetworkResponse<T> {
        if (this.isSuccess && this.data != null) {
            Timber.v("NetworkResponse: Success -> ${this.data}")
            f(this.data)
        } else if (this.isSuccess) {
            Timber.w("NetworkResponse: Empty")
        } else {
            Timber.w("NetworkResponse: Failure -> ${this.error}")

        }
        return this
    }

    inline fun onEmpty(f: () -> Unit): NetworkResponse<T> {
        if (this.isSuccess && this.data == null) {
            f()
        }
        return this
    }

    inline fun onFailure(f: () -> Unit): NetworkResponse<T> {
        if (!this.isSuccess) {
            f()
        }
        return this
    }
}