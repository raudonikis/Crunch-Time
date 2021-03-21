package com.raudonikis.data_domain.di

import com.raudonikis.data_domain.database.game.daos.GameDao
import com.raudonikis.data_domain.database.game.daos.GameDaoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {

    @Binds
    abstract fun bindGameDao(gameDaoImpl: GameDaoImpl): GameDao
}