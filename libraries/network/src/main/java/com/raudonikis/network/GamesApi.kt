package com.raudonikis.network

import com.raudonikis.network.auth.LoginResponse
import com.raudonikis.network.game.GameStatusResponse
import com.raudonikis.network.search.GameSearchResponse
import com.raudonikis.network.utils.NetworkResponse
import retrofit2.http.*

interface GamesApi {

    @GET("api/v1/games/search")
    suspend fun search(@Query("name") name: String): NetworkResponse<List<GameSearchResponse>>

    @POST("api/v1/game/{id}/status")
    suspend fun updateGameStatus(
        @Path("id") id: Long,
        @Body statusJson: String,
    ): NetworkResponse<GameStatusResponse>

    @POST("api/user/login")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): NetworkResponse<LoginResponse>
}