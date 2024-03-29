package com.raudonikis.data_domain.di

import com.raudonikis.data_domain.activity.cache.UserActivityDao
import com.raudonikis.data_domain.activity.cache.UserActivityDaoImpl
import com.raudonikis.data_domain.game.cache.GameDao
import com.raudonikis.data_domain.game.cache.GameDaoImpl
import com.raudonikis.data_domain.user.cache.UserDao
import com.raudonikis.data_domain.user.cache.UserDaoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

    @Binds
    abstract fun bindGameDao(gameDaoImpl: GameDaoImpl): GameDao

    @Binds
    abstract fun bindUserActivityDao(activityDaoImpl: UserActivityDaoImpl): UserActivityDao

    @Binds
    abstract fun bindUserDao(userDaoImpl: UserDaoImpl): UserDao
}