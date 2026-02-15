package com.systematics.zakatcalculator.presentation.screens.activities.gold_activity.gold.events

import com.systematics.zakatcalculator.presentation.screens.activities.gold_activity.gold.state.GoldTab

sealed class GoldEvent {
    object TogglePaidStatus : GoldEvent()
    data class UpdateTab(val tab: GoldTab) : GoldEvent()
    data class UpdateRequirement1(val value: Boolean) : GoldEvent()
    data class UpdateRequirement2(val value: Boolean) : GoldEvent()
    data class UpdateRequirement3(val value: Boolean) : GoldEvent()
    data class UpdateGoldQuantity(val value: String) : GoldEvent()
    data class UpdateGoldPrice(val value: String) : GoldEvent()
    data class UpdateNisabType(val value: String) : GoldEvent()
    object Calculate : GoldEvent()
    object ResetCalculation : GoldEvent()
}
