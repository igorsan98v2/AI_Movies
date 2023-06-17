package com.ygs.data.remote.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ygs.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter.Factory
@Module
@InstallIn(SingletonComponent::class)
object RetrofitProvider {
    @Provides
    fun provideMediaType(): MediaType = "application/json".toMediaType()

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun provideConverterFactory(contentType: MediaType): Factory = json.asConverterFactory(contentType)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(factory: Factory, httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(com.ygs.utils.Constants.BASE_URL)
            .addConverterFactory(factory)
            .client(httpClient)
            .build()


}