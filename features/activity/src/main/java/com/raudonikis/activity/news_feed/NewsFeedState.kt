package com.raudonikis.activity.news_feed

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.activity.models.UserActivity

sealed class NewsFeedState {
    object Disabled : NewsFeedState()
    data class NewsFeed(val newsFeedOutcome: Outcome<List<UserActivity>>) : NewsFeedState()
}
