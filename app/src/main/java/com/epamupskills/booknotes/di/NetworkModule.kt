package com.epamupskills.booknotes.di

import com.epamupskills.book_notes.data.api.GoogleBooksApiService
import com.epamupskills.booknotes.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TIMEOUT_SECONDS = 5L
    private val json = Json {
        prettyPrint = true
        coerceInputValues = true
        ignoreUnknownKeys = true
    }
    private val contentType = "application/json".toMediaType()
    private val apiKeyHeaderName = "API_KEY"

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header(apiKeyHeaderName, BuildConfig.API_KEY)
                    .build()
                chain.proceed(request)
            }.build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    @Provides
    @Singleton
    fun provideGoogleBooksApi(retrofit: Retrofit): GoogleBooksApiService =
        retrofit.create(GoogleBooksApiService::class.java)
}