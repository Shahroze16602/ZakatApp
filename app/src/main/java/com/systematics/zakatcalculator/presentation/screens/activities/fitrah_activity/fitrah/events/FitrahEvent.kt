package com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.events

import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.state.FitrahTab

sealed class FitrahEvent {
    object TogglePaidStatus : FitrahEvent()
    data class UpdateCanGiveRice(val value: Boolean) : FitrahEvent()
    data class UpdateHasExcessFood(val value: Boolean) : FitrahEvent()
    data class UpdateTab(val tab: FitrahTab) : FitrahEvent()
    object IncrementPeople : FitrahEvent()
    object DecrementPeople : FitrahEvent()
    data class UpdatePayWith(val value: String) : FitrahEvent()
    data class UpdateUnit(val value: String) : FitrahEvent()
    data class UpdatePrice(val value: String) : FitrahEvent()
    object Calculate : FitrahEvent()
    object ToggleSummary : FitrahEvent()
    object Reset : FitrahEvent()
}