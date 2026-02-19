package com.systematics.zakatcalculator.presentation.screens.activities.splash_activity.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.systematics.zakatcalculator.presentation.screens.activities.splash_activity.splash.content.SplashScreenContent
import com.systematics.zakatcalculator.presentation.screens.activities.splash_activity.splash.events.SplashEvent
import com.systematics.zakatcalculator.presentation.screens.activities.splash_activity.splash.viewmodel.SplashViewModel

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = viewModel(),
    onNavigateHome: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(SplashEvent.Start)
    }

    LaunchedEffect(state.navigateToHome) {
        if (state.navigateToHome) {
            onNavigateHome()
            viewModel.onEvent(SplashEvent.NavigationHandled)
        }
    }

    SplashScreenContent(state = state)
}
