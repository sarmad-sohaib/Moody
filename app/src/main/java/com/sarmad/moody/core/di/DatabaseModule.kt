package com.sarmad.moody.core.di

import android.content.Context
import androidx.room.Room
import com.sarmad.moody.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
                context = context,
                klass = AppDatabase::class.java,
                name = "moody_database"
            ).fallbackToDestructiveMigration(false).build()

    @Provides
    @Singleton
    fun provideMoodDao(appDatabase: AppDatabase) = appDatabase.moodDao()
}
