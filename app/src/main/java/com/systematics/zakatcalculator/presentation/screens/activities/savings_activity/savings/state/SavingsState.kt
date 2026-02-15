package com.systematics.zakatcalculator.presentation.screens.activities.savings_activity.savings.state

import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.state.FitrahTab

data class SavingsState(
    val isPaid: Boolean = false,
    val selectedTab: FitrahTab = FitrahTab.Calculator,
    val savings: String = "",
    val interests: String = "",
    val goldPrice: String = "",
    val requirement1: Boolean = false,
    val requirement2: Boolean = false,
    val requirement3: Boolean = false,
)
