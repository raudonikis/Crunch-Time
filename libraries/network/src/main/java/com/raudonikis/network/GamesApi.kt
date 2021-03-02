package com.raudonikis.network

import com.haroldadmin.cnradapter.NetworkResponse
import com.raudonikis.network.api.responses.GameResponse
import com.raudonikis.network.api.responses.IgdbErrorResponse
import com.raudonikis.network.auth.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface GamesApi {

    @POST("games")
    suspend fun getGames(@Body requestBody: String = "fields name; limit 10;"): NetworkResponse<List<GameResponse>, IgdbErrorResponse>

    @POST("api/user/login")
    suspend fun login(@Query("email") email: String, @Query("password") password: String): LoginResponse

    /*@POST
    suspend fun authorize(
        @Url url: String = GamesApiConstants.Authorization.Endpoints.GET_TOKEN,
        @Query(GamesApiConstants.Authorization.Query.KEY_CLIENT_ID) clientId: String = GamesApiConstants.Authorization.Query.VALUE_CLIENT_ID,
        @Query(GamesApiConstants.Authorization.Query.KEY_CLIENT_SECRET) clientSecret: String = GamesApiConstants.Authorization.Query.VALUE_CLIENT_SECRET,
        @Query(GamesApiConstants.Authorization.Query.KEY_GRANT_TYPE) grantType: String = GamesApiConstants.Authorization.Query.VALUE_GRANT_TYPE,
    ): NetworkResponse<LoginResponse, IgdbErrorResponse>*/
}