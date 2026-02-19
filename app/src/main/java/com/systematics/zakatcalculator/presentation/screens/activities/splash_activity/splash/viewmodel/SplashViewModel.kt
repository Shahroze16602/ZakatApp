package com.systematics.zakatcalculator.presentation.screens.activities.splash_activity.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.systematics.zakatcalculator.presentation.screens.activities.splash_activity.splash.events.SplashEvent
import com.systematics.zakatcalculator.presentation.screens.activities.splash_activity.splash.state.SplashState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val SPLASH_DURATION_MS = 3_000L
private const val MAX_PROGRESS = 100

class SplashViewModel : ViewModel() {
    private val _state = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState> = _state.asStateFlow()

    private var hasStarted = false

    fun onEvent(event: SplashEvent) {
        when (event) {
            SplashEvent.Start -> startSplashTimer()
            SplashEvent.NavigationHandled -> _state.update { it.copy(navigateToHome = false) }
        }
    }

    private fun startSplashTimer() {
        if (hasStarted) return
        hasStarted = true
        viewModelScope.launch {
            val stepDelay = SPLASH_DURATION_MS / MAX_PROGRESS
            for (progress in 1..MAX_PROGRESS) {
                _state.update { it.copy(progress = progress) }
                delay(stepDelay)
            }
            _state.update { it.copy(navigateToHome = true) }
        }
    }
}
