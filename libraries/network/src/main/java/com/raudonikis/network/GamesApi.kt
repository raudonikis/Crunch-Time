package com.raudonikis.network

import com.raudonikis.network.activity.UserActivityResponse
import com.raudonikis.network.auth.LoginResponse
import com.raudonikis.network.game.GameResponse
import com.raudonikis.network.game_status.GameStatusResponse
import com.raudonikis.network.game_search.GameSearchResponse
import com.raudonikis.network.utils.NetworkResponse
import retrofit2.http.*

interface GamesApi {

    @GET("api/v1/games/search")
    suspend fun search(@Query("name") name: String): NetworkResponse<List<GameSearchResponse>>

    @GET("api/v1/game/{id}")
    suspend fun getGame(@Path("id") id: Long): NetworkResponse<GameResponse>

    @POST("api/v1/game/{id}/status")
    suspend fun updateGameStatus(
        @Path("id") id: Long,
        @Body statusJson: String,
    ): NetworkResponse<GameStatusResponse>

    @GET("api/v1/activities")
    suspend fun getUserActivities(): NetworkResponse<List<UserActivityResponse>>

    @POST("api/user/login")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): NetworkResponse<LoginResponse>
}