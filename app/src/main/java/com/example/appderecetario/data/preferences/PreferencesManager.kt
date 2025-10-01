package com.example.appderecetario.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("app_preferences")

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val ONBOARDING_SHOWN = booleanPreferencesKey("onboarding_shown")
    }

    val onboardingShown: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[ONBOARDING_SHOWN] ?: false
    }

    suspend fun setOnboardingShown(value: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[ONBOARDING_SHOWN] = value
        }
    }
}