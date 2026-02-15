package com.systematics.zakatcalculator.presentation.screens.activities.home_activity.learn

import androidx.compose.runtime.Composable
import com.systematics.zakatcalculator.presentation.screens.activities.home_activity.learn.content.LearnScreenContent

@Composable
fun LearnScreen(onBackClick: () -> Unit = {}) {
    LearnScreenContent(onBackClick = onBackClick)
}
