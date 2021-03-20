package com.raudonikis.network.di

import com.raudonikis.network.activity.UserActivityDataResponse
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

    /*@Provides
    internal fun provideNetworkResponseAdapterFactory(): NetworkResponseAdapterFactory {
        return NetworkResponseAdapterFactory()
    }*/

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
        userActivityDataResponseAdapter: PolymorphicJsonAdapterFactory<UserActivityDataResponse>,
        kotlinJsonAdapterFactory: KotlinJsonAdapterFactory
    ): Moshi {
        return Moshi.Builder()
            .add(userActivityDataResponseAdapter)
            .addLast(kotlinJsonAdapterFactory)
            .build()
    }

    @Provides
    internal fun provideKotlinJsonAdapter(): KotlinJsonAdapterFactory {
        return KotlinJsonAdapterFactory()
    }

    @Provides
    internal fun provideUserActivityDataResponseAdapter(): PolymorphicJsonAdapterFactory<UserActivityDataResponse> {
        return PolymorphicJsonAdapterFactory.of(
            UserActivityDataResponse::class.java,
            UserActivityDataResponse.LABEL_ACTION
        )
            .withSubtype(
                UserActivityDataResponse.ActionGameStatusUpdatedResponse::class.java,
                UserActivityDataResponse.ACTION_GAME_STATUS_UPDATED
            )
            .withSubtype(
                UserActivityDataResponse.ActionGameRankedResponse::class.java,
                UserActivityDataResponse.ACTION_GAME_RANKED
            )
    }
}