package com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.events

import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.state.FitrahPayWith
import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.state.FitrahUnit
import com.systematics.zakatcalculator.presentation.screens.models.ZakatTab

sealed class FitrahEvent {
    object TogglePaidStatus : FitrahEvent()
    data class UpdateCanGiveRice(val value: Boolean) : FitrahEvent()
    data class UpdateHasExcessFood(val value: Boolean) : FitrahEvent()
    data class UpdateTab(val tab: ZakatTab) : FitrahEvent()
    object IncrementPeople : FitrahEvent()
    object DecrementPeople : FitrahEvent()
    data class UpdatePayWith(val value: FitrahPayWith) : FitrahEvent()
    data class UpdateUnit(val value: FitrahUnit) : FitrahEvent()
    data class UpdatePrice(val value: String) : FitrahEvent()
    object Calculate : FitrahEvent()
    object ToggleSummary : FitrahEvent()
    object Reset : FitrahEvent()
}
