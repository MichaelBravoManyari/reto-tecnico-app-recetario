package com.example.appderecetario.usecase

import com.example.appderecetario.data.preferences.PreferencesManager
import com.example.appderecetario.domain.usecase.GetOnboardingShownUseCase
import com.example.appderecetario.domain.usecase.SetOnboardingShownUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class OnboardingUseCasesTest {

    private val preferencesManager: PreferencesManager = mockk(relaxed = true)
    private lateinit var setUseCase: SetOnboardingShownUseCase
    private lateinit var getUseCase: GetOnboardingShownUseCase

    @Before
    fun setUp() {
        setUseCase = SetOnboardingShownUseCase(preferencesManager)
        getUseCase = GetOnboardingShownUseCase(preferencesManager)
    }

    @Test
    fun `setOnboardingShown actualiza preferencia`() = runTest {
        coEvery { preferencesManager.setOnboardingShown(true) } returns Unit

        setUseCase(true)

        coVerify { preferencesManager.setOnboardingShown(true) }
    }

    @Test
    fun `getOnboardingShown emite valor correcto`() = runTest {
        every { preferencesManager.onboardingShown } returns flowOf(true)

        val result = getUseCase().first()

        assertTrue(result)
    }
}