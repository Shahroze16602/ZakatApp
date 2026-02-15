package com.systematics.zakatcalculator.presentation.screens.activities.rikaz_activity.rikaz

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.systematics.zakatcalculator.presentation.screens.activities.rikaz_activity.rikaz.content.RikazScreenContent
import com.systematics.zakatcalculator.presentation.screens.activities.rikaz_activity.rikaz.viewmodel.RikazViewModel

@Composable
fun RikazScreen(viewModel: RikazViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    RikazScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}
