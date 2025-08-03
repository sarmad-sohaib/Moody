package com.sarmad.moody.core.di

import android.content.Context
import android.content.SharedPreferences
import com.sarmad.moody.data.local.preferences.SharedPreferenceManager
import com.sarmad.moody.domain.preferences.UserPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {
    @Singleton
    @Provides
    fun provideSharedPreference(
        @ApplicationContext context: Context,
    ): SharedPreferences = context.getSharedPreferences(
        "app-prefs",
        Context.MODE_PRIVATE
    )

    @Singleton
    @Provides
    fun providesSharedPreferenceManager(
        sharedPreferences: SharedPreferences,
    ): UserPreferencesManager = SharedPreferenceManager(
        sharedPreferences = sharedPreferences
    )
}
