package com.raudonikis.network.utils

import retrofit2.HttpException
import timber.log.Timber


suspend fun <T> safeNetworkResponse(call: suspend () -> NetworkResponse<T>): NetworkResponse<T> {
    return try {
        call()
    } catch (e: HttpException) {
        Timber.w("Error -> ${e.code()} -> ${e.message()}")
        NetworkResponse()
    } catch (e: Exception) {
        Timber.w("Error -> ${e.message}")
        NetworkResponse()
    }
}