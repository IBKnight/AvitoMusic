package com.avito.avitomusic.common.di

import com.avito.avitomusic.features.music_list.data.ApiMusicDataSource
import com.avito.avitomusic.features.music_list.data.ApiMusicListRepository
import com.avito.avitomusic.features.music_list.domain.repository.IApiMusicListRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.deezer.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideApiMusicDataSource(retrofit: Retrofit): ApiMusicDataSource {
        return retrofit.create(ApiMusicDataSource::class.java)
    }

    @Provides
    @Singleton
    fun provideApiMusicListRepository(apiMusicDataSource: ApiMusicDataSource): IApiMusicListRepository {
        return ApiMusicListRepository(apiMusicDataSource)
    }

}