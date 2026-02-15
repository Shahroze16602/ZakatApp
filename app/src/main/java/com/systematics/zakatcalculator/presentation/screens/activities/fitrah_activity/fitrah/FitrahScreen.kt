package com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.content.FitrahScreenContent
import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.viewmodel.FitrahViewModel

@Composable
fun FitrahScreen(viewModel: FitrahViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    FitrahScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}