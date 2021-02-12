package com.raudonikis.network.igdb

import com.raudonikis.network.igdb.responses.GameResponse
import com.raudonikis.network.igdb.responses.IgdbTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface IgdbApi {

    @POST("games")
    suspend fun getGames(@Body requestBody: String = "fields name; limit 10;"): List<GameResponse>

    @POST
    suspend fun authorize(
        @Url url: String = IgdbApiConstants.Authorization.Endpoints.GET_TOKEN,
        @Query(IgdbApiConstants.Authorization.Query.KEY_CLIENT_ID) clientId: String = IgdbApiConstants.Authorization.Query.VALUE_CLIENT_ID,
        @Query(IgdbApiConstants.Authorization.Query.KEY_CLIENT_SECRET) clientSecret: String = IgdbApiConstants.Authorization.Query.VALUE_CLIENT_SECRET,
        @Query(IgdbApiConstants.Authorization.Query.KEY_GRANT_TYPE) grantType: String = IgdbApiConstants.Authorization.Query.VALUE_GRANT_TYPE,
    ): IgdbTokenResponse
}