package com.systematics.zakatcalculator.presentation.screens.activities.savings_activity.savings.events

import com.systematics.zakatcalculator.presentation.screens.models.ZakatTab

sealed class SavingsEvent {
    data class UpdateSavings(val savings: String) : SavingsEvent()
    data class UpdateInterests(val interests: String) : SavingsEvent()
    data class UpdateGoldPrice(val goldPrice: String) : SavingsEvent()
    data class UpdateRequirement1(val value: Boolean) : SavingsEvent()
    data class UpdateRequirement2(val value: Boolean) : SavingsEvent()
    data class UpdateRequirement3(val value: Boolean) : SavingsEvent()
    data class UpdateTab(val tab: ZakatTab) : SavingsEvent()
    object TogglePaidStatus : SavingsEvent()
    object Calculate : SavingsEvent()
    object ToggleSummary : SavingsEvent()
    object ResetCalculation : SavingsEvent()
}
