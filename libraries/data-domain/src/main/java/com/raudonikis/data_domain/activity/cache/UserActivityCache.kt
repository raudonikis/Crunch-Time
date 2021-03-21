package com.raudonikis.data_domain.activity.cache

import com.raudonikis.data_domain.activity.cache.daos.UserActivityDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserActivityCache @Inject constructor(
    private val userActivityDao: UserActivityDao,
)