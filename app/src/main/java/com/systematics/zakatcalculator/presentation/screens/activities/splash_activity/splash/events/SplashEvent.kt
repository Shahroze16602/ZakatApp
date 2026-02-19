package com.systematics.zakatcalculator.presentation.screens.activities.splash_activity.splash.events

sealed class SplashEvent {
    object Start : SplashEvent()
    object NavigationHandled : SplashEvent()
}
