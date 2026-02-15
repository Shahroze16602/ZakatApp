package com.systematics.zakatcalculator.presentation.screens.activities.savings_activity.savings.state

import com.systematics.zakatcalculator.presentation.screens.models.ZakatTab

data class SavingsState(
    val isPaid: Boolean = false,
    val selectedTab: ZakatTab = ZakatTab.Calculator,
    val savings: String = "",
    val interests: String = "",
    val goldPrice: String = "",
    val requirement1: Boolean = false,
    val requirement2: Boolean = false,
    val requirement3: Boolean = false,
    val calculationResult: SavingsCalculationResult? = null,
    val showSummary: Boolean = false
)

sealed class SavingsCalculationResult {
    data class Success(val amount: String) : SavingsCalculationResult()
    object BelowNisab : SavingsCalculationResult()
}
