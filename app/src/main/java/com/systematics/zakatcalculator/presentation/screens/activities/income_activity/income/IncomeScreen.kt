package com.systematics.zakatcalculator.presentation.screens.activities.income_activity.income

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.systematics.zakatcalculator.presentation.screens.activities.income_activity.income.content.IncomeScreenContent
import com.systematics.zakatcalculator.presentation.screens.activities.income_activity.income.viewmodel.IncomeViewModel

@Composable
fun IncomeScreen(viewModel: IncomeViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    IncomeScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}
