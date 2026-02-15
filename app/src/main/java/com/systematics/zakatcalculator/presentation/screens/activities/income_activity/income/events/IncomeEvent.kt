package com.systematics.zakatcalculator.presentation.screens.activities.income_activity.income.events

import com.systematics.zakatcalculator.presentation.screens.models.ZakatTab

sealed class IncomeEvent {
    data class UpdateIncome(val income: String) : IncomeEvent()
    data class UpdateExpense(val expense: String) : IncomeEvent()
    data class UpdateGoldPrice(val goldPrice: String) : IncomeEvent()
    data class UpdateRequirement1(val value: Boolean) : IncomeEvent()
    data class UpdateRequirement2(val value: Boolean) : IncomeEvent()
    data class UpdateRequirement3(val value: Boolean) : IncomeEvent()
    data class UpdateRequirement4(val value: Boolean) : IncomeEvent()
    data class UpdateTab(val tab: ZakatTab) : IncomeEvent()
    object TogglePaidStatus : IncomeEvent()
    object Calculate : IncomeEvent()
    object ToggleSummary : IncomeEvent()
    object ResetCalculation : IncomeEvent()
}
