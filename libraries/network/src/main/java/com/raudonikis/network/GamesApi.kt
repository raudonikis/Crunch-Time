package com.raudonikis.network

import com.raudonikis.network.activity.UserActivityResponse
import com.raudonikis.network.auth.UserResponse
import com.raudonikis.network.auth.login.LoginRequestBody
import com.raudonikis.network.auth.login.LoginResponse
import com.raudonikis.network.auth.register.RegisterRequestBody
import com.raudonikis.network.auth.register.RegisterResponse
import com.raudonikis.network.game_deals.DealSearchResponse
import com.raudonikis.network.game.GameResponse
import com.raudonikis.network.game_review.GameReviewInfoResponse
import com.raudonikis.network.game_review.GameReviewPostResponse
import com.raudonikis.network.game_review.GameReviewRequestBody
import com.raudonikis.network.game_status.GameStatusResponse
import com.raudonikis.network.game_search.GameSearchResponse
import com.raudonikis.network.game_status.GameStatusRequestBody
import com.raudonikis.network.popular_games.PopularGameResponse
import com.raudonikis.network.user.UserSearchResponse
import com.raudonikis.network.utils.NetworkResponse
import retrofit2.http.*

interface GamesApi {

    /**
     * Game
     */
    @GET("api/v1/games/search")
    suspend fun search(@Query("name") name: String): NetworkResponse<List<GameSearchResponse>>

    @GET("api/v1/game/{id}")
    suspend fun getGame(@Path("id") id: Long): NetworkResponse<GameResponse>

    @POST("api/v1/game/{id}/status")
    suspend fun updateGameStatus(
        @Path("id") id: Long,
        @Body status: GameStatusRequestBody,
    ): NetworkResponse<GameStatusResponse>

    @GET("api/v1/games/status/{type}")
    suspend fun getGameCollection(
        @Path("type") collectionType: String,
    ): NetworkResponse<List<GameSearchResponse>>

    /**
     * Reviews
     */
    @GET("api/v1/game/{id}/reviews")
    suspend fun getGameReviewInfo(
        @Path("id") id: Long
    ): NetworkResponse<GameReviewInfoResponse>

    @POST("api/v1/reviews")
    suspend fun postReview(
        @Body review: GameReviewRequestBody
    ): NetworkResponse<GameReviewPostResponse>

    /**
     * Popular/Trending lists
     */
    @GET("api/v1/games/popular")
    suspend fun getPopularGames(): NetworkResponse<List<PopularGameResponse>>

    /**
     * Activity
     */
    @GET("api/v1/activities")
    suspend fun getUserActivity(): NetworkResponse<List<UserActivityResponse>>

    /**
     * News feed
     */
    @GET("api/v1/user/news-feed")
    suspend fun getNewsFeed(): NetworkResponse<List<UserActivityResponse>>

    /**
     * Deals
     */
    @GET("api/v1/deal/search")
    suspend fun searchGameDeals(@Query("name") name: String): List<DealSearchResponse>

    /**
     * Users
     */
    @GET("api/v1/user/search")
    suspend fun searchUsers(@Query("name") name: String): NetworkResponse<List<UserResponse>>

    @POST("api/v1/user/follow/{uuid}")
    suspend fun followUser(@Path("uuid") uuid: String): NetworkResponse<List<Nothing>>

    @DELETE("api/v1/user/follow/{uuid}")
    suspend fun unfollowUser(@Path("uuid") uuid: String): NetworkResponse<List<Nothing>>

    @GET("api/v1/user/followers")
    suspend fun getFollowingUsers(): NetworkResponse<List<UserResponse>>

    /**
     * Authentication
     */
    @POST("api/user/login")
    suspend fun login(
        @Body loginBody: LoginRequestBody
    ): NetworkResponse<LoginResponse>

    @POST("api/v1/user/logout")
    suspend fun logout(): NetworkResponse<List<Nothing>>

    @POST("api/user/register")
    suspend fun register(
        @Body registerBody: RegisterRequestBody
    ): NetworkResponse<RegisterResponse>
}