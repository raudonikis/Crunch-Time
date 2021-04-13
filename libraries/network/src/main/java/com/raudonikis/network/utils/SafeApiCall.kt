package com.raudonikis.network.utils

import retrofit2.HttpException
import timber.log.Timber

suspend fun <T> safeNetworkResponse(call: suspend () -> NetworkResponse<T>): NetworkResponse<T> {
    return try {
        call()
    } catch (e: HttpException) {
        Timber.w("safeNetworkResponse Error -> ${e.code()} -> ${e.message()}")
        NetworkResponse(isSuccess = false, error = NetworkErrorResponse(e.code(), e.message()))
    } catch (e: Exception) {
        Timber.w("safeNetworkResponse Error -> ${e.message}")
        NetworkResponse(isSuccess = false, error = NetworkErrorResponse(message = e.message ?: ""))
    }
}