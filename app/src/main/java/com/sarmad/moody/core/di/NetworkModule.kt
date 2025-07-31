package com.sarmad.moody.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesHttpClient() = HttpClient(
        engineFactory = Android
    ) {
        install(plugin = ContentNegotiation) {
            json()
        }

        install(plugin = HttpTimeout) {
            requestTimeoutMillis = 10000L // 10 seconds
        }
    }
}
