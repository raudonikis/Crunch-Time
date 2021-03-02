package com.raudonikis.network.di

import com.raudonikis.network.GamesApi
import com.raudonikis.network.utils.GamesApiConstants
import com.raudonikis.network.utils.GamesApiInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGamesApi(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory,
        scalarsConverterFactory: ScalarsConverterFactory,
    ): GamesApi {
        return Retrofit.Builder()
            .baseUrl(GamesApiConstants.BASE_URL)
            .client(okHttpClient)
//            .addCallAdapterFactory(networkResponseAdapterFactory)
            .addConverterFactory(scalarsConverterFactory)
            .addConverterFactory(moshiConverterFactory)
            .build()
            .create(GamesApi::class.java)
    }

    @Provides
    internal fun provideOkHttpClient(
        gamesApiInterceptor: GamesApiInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(gamesApiInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    internal fun provideHtppLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    }
}