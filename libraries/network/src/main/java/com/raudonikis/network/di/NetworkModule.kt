package com.raudonikis.network.di

import com.raudonikis.network.igdb.IgdbApi
import com.raudonikis.network.igdb.IgdbApiInterceptor
import com.raudonikis.network.igdb.IgdbApiConstants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
    fun provideIgdbApi(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory,
        scalarsConverterFactory: ScalarsConverterFactory,
    ): IgdbApi {
        return Retrofit.Builder()
            .baseUrl(IgdbApiConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(scalarsConverterFactory)
            .addConverterFactory(moshiConverterFactory)
            .build()
            .create(IgdbApi::class.java)
    }

    @Provides
    internal fun provideOkHttpClient(
        igdbApiInterceptor: IgdbApiInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(igdbApiInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    internal fun provideHtppLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    }

    @Provides
    internal fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    internal fun provideScalarsConverterFactory(): ScalarsConverterFactory {
        return ScalarsConverterFactory.create()
    }

    @Provides
    internal fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }
}