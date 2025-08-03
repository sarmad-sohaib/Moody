package com.sarmad.moody.core.di

import com.sarmad.moody.data.local.entity.Mood
import com.sarmad.moody.data.repository.insight.InsightsExtractor
import com.sarmad.moody.data.repository.insight.MoodInsightsExtractor
import com.sarmad.moody.domain.dataholder.MoodInsight
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class InsightsModule {

    @Binds
    @Singleton
    abstract fun bindInsightsRepository(
        moodInsightsExtractor: MoodInsightsExtractor,
    ): InsightsExtractor<Mood, MoodInsight>
}