package com.systematics.zakatcalculator.presentation.screens.activities.rikaz_activity.rikaz.events

import com.systematics.zakatcalculator.presentation.screens.models.ZakatTab

sealed class RikazEvent {
    data class UpdateTreasureValue(val value: String) : RikazEvent()
    data class UpdateRequirement1(val value: Boolean) : RikazEvent()
    data class UpdateTab(val tab: ZakatTab) : RikazEvent()
    object TogglePaidStatus : RikazEvent()
    object CalculateZakat : RikazEvent()
    object ToggleSummary : RikazEvent()
    object ResetCalculation : RikazEvent()
}
