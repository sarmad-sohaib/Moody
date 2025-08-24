package com.sarmad.moody.domain.preferences

// Average: Should be in /data
interface UserPreferencesManager {
    fun getCurrentAppTheme(): AppTheme

    fun setCurrentAppTheme(appTheme: AppTheme)
}