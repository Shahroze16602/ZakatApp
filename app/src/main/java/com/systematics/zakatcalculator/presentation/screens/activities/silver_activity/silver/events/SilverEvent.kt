package com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.silver.events

import com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.silver.state.SilverTab

sealed class SilverEvent {
    object TogglePaidStatus : SilverEvent()
    data class UpdateTab(val tab: SilverTab) : SilverEvent()
    data class UpdateRequirement1(val value: Boolean) : SilverEvent()
    data class UpdateRequirement2(val value: Boolean) : SilverEvent()
    data class UpdateRequirement3(val value: Boolean) : SilverEvent()
    data class UpdateSilverQuantity(val value: String) : SilverEvent()
    data class UpdateSilverPrice(val value: String) : SilverEvent()
    object Calculate : SilverEvent()
    object ResetCalculation : SilverEvent()
}
