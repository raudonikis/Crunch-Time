package com.raudonikis.common_ui.user_item

import com.raudonikis.data_domain.user.User

object UserItemMapper {

    /**
     * From [User] to [UserItem]
     */
    fun fromUser(user: User): UserItem {
        return UserItem(user)
    }

    /**
     * From a list of [User] to a list of [UserItem]
     */
    fun fromUserList(users: List<User>): List<UserItem> {
        return users.map { fromUser(it) }
    }
}