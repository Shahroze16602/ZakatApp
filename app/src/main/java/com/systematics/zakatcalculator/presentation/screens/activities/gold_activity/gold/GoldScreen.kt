package com.systematics.zakatcalculator.presentation.screens.activities.gold_activity.gold

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.systematics.zakatcalculator.presentation.screens.activities.gold_activity.gold.content.GoldScreenContent
import com.systematics.zakatcalculator.presentation.screens.activities.gold_activity.gold.viewmodel.GoldViewModel

@Composable
fun GoldScreen(viewModel: GoldViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    GoldScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}
