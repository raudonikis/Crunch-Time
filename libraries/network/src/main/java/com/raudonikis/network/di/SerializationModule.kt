package com.raudonikis.network.di

import com.raudonikis.network.activity.UserActivityResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object SerializationModule {


    @Provides
    internal fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    internal fun provideScalarsConverterFactory(): ScalarsConverterFactory {
        return ScalarsConverterFactory.create()
    }

    @Provides
    internal fun provideMoshi(
        userActivityResponseAdapter: PolymorphicJsonAdapterFactory<UserActivityResponse>,
        kotlinJsonAdapterFactory: KotlinJsonAdapterFactory
    ): Moshi {
        return Moshi.Builder()
            .add(userActivityResponseAdapter)
            .addLast(kotlinJsonAdapterFactory)
            .build()
    }

    @Provides
    internal fun provideKotlinJsonAdapter(): KotlinJsonAdapterFactory {
        return KotlinJsonAdapterFactory()
    }

    @Provides
    internal fun provideUserActivityResponseAdapter(): PolymorphicJsonAdapterFactory<UserActivityResponse> {
        return PolymorphicJsonAdapterFactory.of(
            UserActivityResponse::class.java,
            UserActivityResponse.LABEL_ACTION
        )
            .withSubtype(
                UserActivityResponse.UserActivityGameStatusResponse::class.java,
                UserActivityResponse.ACTION_GAME_STATUS_UPDATED
            )
            .withSubtype(
                UserActivityResponse.UserActivityGameStatusResponse::class.java,
                UserActivityResponse.ACTION_GAME_RANKED
            )
    }
}