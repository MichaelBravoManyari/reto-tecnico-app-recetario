package com.example.appderecetario.domain.usecase

import com.example.appderecetario.presentation.splash.SplashDestination
import javax.inject.Inject

class DecideSplashDestinationUseCase @Inject constructor() {
    operator fun invoke(onboardingShown: Boolean): SplashDestination {
        return if (onboardingShown) SplashDestination.Home else SplashDestination.Onboarding
    }
}