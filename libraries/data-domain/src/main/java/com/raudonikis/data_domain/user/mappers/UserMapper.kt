package com.raudonikis.data_domain.user.mappers

import com.raudonikis.data_domain.user.User
import com.raudonikis.network.auth.UserResponse

object UserMapper {

    /**
     * From [UserResponse] to [User]
     */
    fun fromUserResponse(userResponse: UserResponse): User {
        return User(
            uuid = userResponse.uuid,
            name = userResponse.name,
            email = userResponse.email,
            isFollowed = userResponse.isFollowed,
        )
    }

    /**
     * From a list of [UserResponse] to a list of [User]
     */
    fun fromUserResponseList(userResponses: List<UserResponse>): List<User> {
        return userResponses.map { fromUserResponse(it) }
    }
}