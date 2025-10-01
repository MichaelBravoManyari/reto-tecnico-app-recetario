package com.example.appderecetario.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appderecetario.data.preferences.PreferencesManager
import com.example.appderecetario.domain.usecase.DecideSplashDestinationUseCase
import com.example.appderecetario.domain.usecase.GetOnboardingShownUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SplashDestination {
    object Onboarding : SplashDestination()
    object Home : SplashDestination()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getOnboardingShownUseCase: GetOnboardingShownUseCase,
    private val decideSplashDestinationUseCase: DecideSplashDestinationUseCase
) : ViewModel() {

    private val _destination = MutableStateFlow<SplashDestination?>(null)
    val destination: StateFlow<SplashDestination?> = _destination

    init {
        viewModelScope.launch {
            delay(2000)
            getOnboardingShownUseCase().collect { shown ->
                _destination.value = decideSplashDestinationUseCase(shown)
            }
        }
    }
}