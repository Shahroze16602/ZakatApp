package com.systematics.zakatcalculator.presentation.screens.activities.income_activity.income.state

import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.state.FitrahTab

data class IncomeState(
    val isPaid: Boolean = false,
    val selectedTab: FitrahTab = FitrahTab.Calculator,
    val income: String = "",
    val expense: String = "",
    val goldPrice: String = "45000",
    val requirement1: Boolean = false,
    val requirement2: Boolean = false,
    val requirement3: Boolean = false,
    val requirement4: Boolean = false
)
