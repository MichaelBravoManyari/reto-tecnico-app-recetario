package com.example.appderecetario.domain.usecase

import com.example.appderecetario.data.preferences.PreferencesManager
import javax.inject.Inject

class SetOnboardingShownUseCase @Inject constructor(
    private val preferencesManager: PreferencesManager
) {
    suspend operator fun invoke(value: Boolean) {
        preferencesManager.setOnboardingShown(value)
    }
}