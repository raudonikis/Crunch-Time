package com.raudonikis.network.di

import com.raudonikis.network.igdb.IgdbApi
import com.raudonikis.network.igdb.IgdbApiInterceptor
import com.raudonikis.network.igdb.IgdbApiConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideIgdbApi(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): IgdbApi {
        return Retrofit.Builder()
            .baseUrl(IgdbApiConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
            .create(IgdbApi::class.java)
    }

    @Provides
    internal fun provideOkHttpClient(interceptor: IgdbApiInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    internal fun provideMoshiConverterFactory(): MoshiConverterFactory {
        return MoshiConverterFactory.create()
    }
}