package com.raudonikis.network.utils

import com.raudonikis.data.UserPreferences
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

internal class GamesApiInterceptor @Inject constructor(
    private val userPreferences: UserPreferences
) : Interceptor {

    private val headers: Map<String, String>
        get() = mapOf(
            GamesApiConstants.Header.KEY_AUTHORIZATION to "${GamesApiConstants.Header.VALUE_AUTHORIZATION}${userPreferences.accessToken}",
            GamesApiConstants.Header.KEY_CONTENT_TYPE to GamesApiConstants.Header.VALUE_CONTENT_TYPE,
        )
    private val queries: Map<String, String>
        get() = mapOf()

    override fun intercept(chain: Interceptor.Chain): Response {
        with(addHeaders(chain.request())) {
            addQueries(url)
                .also { url ->
                    return chain.proceed(
                        newBuilder()
                            .url(url)
                            .build()
                    )
                }
        }
    }

    private fun addHeaders(request: Request): Request {
        return request
            .newBuilder()
            .apply {
                headers.map { header ->
                    addHeader(header.key, header.value)
                }
            }
            .build()
    }

    private fun addQueries(url: HttpUrl): HttpUrl {
        return url
            .newBuilder()
            .apply {
                queries.map { query ->
                    addQueryParameter(query.key, query.value)
                }
            }
            .build()
    }
}