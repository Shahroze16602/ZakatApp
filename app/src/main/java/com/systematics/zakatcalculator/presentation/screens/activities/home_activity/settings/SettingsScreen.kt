package com.systematics.zakatcalculator.presentation.screens.activities.home_activity.settings

import androidx.compose.runtime.Composable
import com.systematics.zakatcalculator.presentation.screens.activities.home_activity.settings.content.SettingsScreenContent

@Composable
fun SettingsScreen(onBackClick: () -> Unit = {}) {
    SettingsScreenContent(onBackClick = onBackClick)
}
