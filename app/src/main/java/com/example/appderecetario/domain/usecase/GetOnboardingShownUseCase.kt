package com.example.appderecetario.domain.usecase

import com.example.appderecetario.data.preferences.PreferencesManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOnboardingShownUseCase @Inject constructor(
    private val preferencesManager: PreferencesManager
) {
    operator fun invoke(): Flow<Boolean> = preferencesManager.onboardingShown
}