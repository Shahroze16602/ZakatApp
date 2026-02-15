package com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.silver

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.silver.content.SilverScreenContent
import com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.silver.viewmodel.SilverViewModel

@Composable
fun SilverScreen(viewModel: SilverViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    SilverScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}
