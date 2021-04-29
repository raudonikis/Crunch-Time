package com.raudonikis.core.providers.di

import com.raudonikis.core.providers.CoroutineDispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {

    @Provides
    fun provideCoroutineDispatcherProvider(): CoroutineDispatcherProvider {
        return CoroutineDispatcherProvider(
            ioDispatcher = Dispatchers.IO,
            mainDispatcher = Dispatchers.Main,
            defaultDispatcher = Dispatchers.Default
        )
    }

    @Provides
    @IODispatcher
    fun provideIODispatcher(coroutineDispatcherProvider: CoroutineDispatcherProvider): CoroutineDispatcher {
        return coroutineDispatcherProvider.ioDispatcher
    }
}