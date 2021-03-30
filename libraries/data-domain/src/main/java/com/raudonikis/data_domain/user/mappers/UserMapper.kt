package com.raudonikis.data_domain.user.mappers

import com.raudonikis.data_domain.user.User
import com.raudonikis.network.user.UserSearchResponse

object UserMapper {

    fun fromUserSearchResponse(userSearchResponse: UserSearchResponse): User {
        return User(
            uuid = userSearchResponse.uuid,
            name = userSearchResponse.name,
            email = userSearchResponse.email,
        )
    }

    fun fromUserSearchResponseList(userSearchResponses: List<UserSearchResponse>): List<User> {
        return userSearchResponses.map { fromUserSearchResponse(it) }
    }
}