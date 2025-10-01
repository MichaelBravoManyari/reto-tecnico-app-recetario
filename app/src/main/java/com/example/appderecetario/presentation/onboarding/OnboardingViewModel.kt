package com.example.appderecetario.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appderecetario.domain.usecase.SetOnboardingShownUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val setOnboardingShownUseCase: SetOnboardingShownUseCase
) : ViewModel() {

    fun finishOnboarding(onFinished: () -> Unit) {
        viewModelScope.launch {
            setOnboardingShownUseCase(true)
            onFinished()
        }
    }
}