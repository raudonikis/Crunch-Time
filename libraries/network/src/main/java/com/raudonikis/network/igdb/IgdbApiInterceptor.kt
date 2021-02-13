package com.raudonikis.network.igdb

import com.raudonikis.data.sharedpreferences.UserPreferences
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

internal class IgdbApiInterceptor @Inject constructor(
    private val userPreferences: UserPreferences
) : Interceptor {

    private val headers: Map<String, String>
        get() = mapOf(
            IgdbApiConstants.Authorization.Header.KEY_CLIENT_ID to IgdbApiConstants.Authorization.Header.VALUE_CLIENT_ID,
            IgdbApiConstants.Authorization.Header.KEY_AUTHORIZATION to "${IgdbApiConstants.Authorization.Header.VALUE_AUTHORIZATION}${userPreferences.accessToken}"
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