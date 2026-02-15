package com.systematics.zakatcalculator.presentation.screens.activities.rikaz_activity.rikaz.events

import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.state.FitrahTab

sealed class RikazEvent {
    data class UpdateTreasureValue(val value: String) : RikazEvent()
    data class UpdateTab(val tab: FitrahTab) : RikazEvent()
    object TogglePaidStatus : RikazEvent()
    object CalculateZakat : RikazEvent()
}
