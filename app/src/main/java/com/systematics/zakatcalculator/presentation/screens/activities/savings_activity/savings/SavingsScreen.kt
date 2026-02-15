package com.systematics.zakatcalculator.presentation.screens.activities.savings_activity.savings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.systematics.zakatcalculator.presentation.screens.activities.savings_activity.savings.content.SavingsScreenContent
import com.systematics.zakatcalculator.presentation.screens.activities.savings_activity.savings.viewmodel.SavingsViewModel

@Composable
fun SavingsScreen(viewModel: SavingsViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    SavingsScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}
