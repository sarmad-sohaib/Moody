package com.sarmad.moody.data.local.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import com.sarmad.moody.domain.preferences.AppTheme
import com.sarmad.moody.domain.preferences.UserPreferencesManager
import javax.inject.Inject

class SharedPreferenceManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : UserPreferencesManager {

    companion object {
        const val KEY_APP_THEME = "app_theme"
    }

    override fun getCurrentAppTheme(): AppTheme {
        val theme = sharedPreferences.getString(KEY_APP_THEME, AppTheme.SYSTEM.name)
        return getAppTheme(theme)
    }

    override fun setCurrentAppTheme(appTheme: AppTheme) {
        sharedPreferences.edit { putString(KEY_APP_THEME, appTheme.name) }
    }

    private fun getAppTheme(theme: String?) =
        when (theme) {
            AppTheme.LIGHT.name -> AppTheme.LIGHT
            AppTheme.DARK.name -> AppTheme.DARK
            else -> AppTheme.SYSTEM
        }


}
