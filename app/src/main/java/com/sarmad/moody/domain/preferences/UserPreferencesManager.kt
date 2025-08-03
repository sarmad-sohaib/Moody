package com.sarmad.moody.domain.preferences

interface UserPreferencesManager {
    fun getCurrentAppTheme(): AppTheme

    fun setCurrentAppTheme(appTheme: AppTheme)
}