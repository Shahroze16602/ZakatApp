package com.systematics.zakatcalculator.presentation.screens.activities.income_activity.income.state

import com.systematics.zakatcalculator.presentation.screens.models.ZakatTab

data class IncomeState(
    val isPaid: Boolean = false,
    val selectedTab: ZakatTab = ZakatTab.Calculator,
    val income: String = "",
    val expense: String = "",
    val goldPrice: String = "45000",
    val requirement1: Boolean = false,
    val requirement2: Boolean = false,
    val requirement3: Boolean = false,
    val requirement4: Boolean = false,
    val calculationResult: IncomeCalculationResult? = null,
    val showSummary: Boolean = false
)

sealed class IncomeCalculationResult {
    data class Success(val amount: String) : IncomeCalculationResult()
    object BelowNisab : IncomeCalculationResult()
}
