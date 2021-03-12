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

/*
suspend fun <T> safeApiCall(call: suspend () -> Response<T>): Outcome<T> {
    return safeApiResult(call)
        .onSuccess { value ->
            Timber.d("SafeApiCall success -> $value")
        }
        .onFailure { */
/*errorCode ->*//*

            Timber.w("SafeApiCall failure ->")
        }
        .onEmpty {
            Timber.w("SafeApiCall empty")
        }
}

private suspend fun <T> safeApiResult(call: suspend () -> Response<T>): Outcome<T> {
    return try {
        val response = call.invoke()
        val data = response.body()
        when {
            // Response successful
            response.isSuccessful -> {
                when {
                    data != null -> Outcome.success(data)
                    response.code() == HttpURLConnection.HTTP_NO_CONTENT -> Outcome.empty()
                    else -> Outcome.empty()
                }
            }
            // Response unsuccessful
            !response.isSuccessful && response.errorBody() != null -> {
                Timber.w("SafeApiCall errorBody -> ${response.errorBody()}")
                Outcome.failure(*/
/*ServerError.GENERIC, response.errorBody().toString()*//*
)
            }
            else -> Outcome.failure(*/
/*ServerError.GENERIC, response.code().toString()*//*
)
        }
    } catch (e: Exception) {
        Timber.w("SafeApiCall exception thrown")
        Outcome.failure(*/
/*ServerError.GENERIC, e.message.toString()*//*
)
    }
}*/
