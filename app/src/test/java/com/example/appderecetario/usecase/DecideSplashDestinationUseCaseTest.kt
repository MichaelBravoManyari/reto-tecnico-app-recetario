package com.example.appderecetario.usecase

import com.example.appderecetario.domain.usecase.DecideSplashDestinationUseCase
import com.example.appderecetario.presentation.splash.SplashDestination
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class DecideSplashDestinationUseCaseTest {
    private lateinit var useCase: DecideSplashDestinationUseCase

    @Before
    fun setUp() {
        useCase = DecideSplashDestinationUseCase()
    }

    @Test
    fun `cuando onboardingShown es true, retorna Home`() {
        val result = useCase(true)
        assertEquals(SplashDestination.Home, result)
    }

    @Test
    fun `cuando onboardingShown es false, retorna Onboarding`() {
        val result = useCase(false)
        assertEquals(SplashDestination.Onboarding, result)
    }
}