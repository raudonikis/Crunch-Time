package com.raudonikis.data_domain.user.mappers

import com.raudonikis.data_domain.user.User
import com.raudonikis.network.auth.UserResponse
import com.raudonikis.network.user.UserSearchResponse

object UserMapper {

    /**
     * From [UserSearchResponse] to [User]
     */
    fun fromUserSearchResponse(userSearchResponse: UserSearchResponse): User {
        return User(
            uuid = userSearchResponse.uuid,
            name = userSearchResponse.name,
            email = userSearchResponse.email,
        )
    }

    /**
     * From a list of [UserSearchResponse] to a list of [User]
     */
    fun fromUserSearchResponseList(userSearchResponses: List<UserSearchResponse>): List<User> {
        return userSearchResponses.map { fromUserSearchResponse(it) }
    }

    /**
     * From [UserResponse] to [User]
     */
    fun fromUserResponse(userResponse: UserResponse): User {
        return User(
            uuid = userResponse.uuid,
            name = userResponse.name,
            email = userResponse.email,
        )
    }

    /**
     * From a list of [UserResponse] to a list of [User]
     */
    fun fromUserResponseList(userResponses: List<UserResponse>): List<User> {
        return userResponses.map { fromUserResponse(it) }
    }
}