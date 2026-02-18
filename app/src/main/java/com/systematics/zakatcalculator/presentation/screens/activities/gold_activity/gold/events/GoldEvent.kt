package com.systematics.zakatcalculator.presentation.screens.activities.gold_activity.gold.events

import androidx.annotation.StringRes
import com.systematics.zakatcalculator.presentation.screens.models.ZakatTab

sealed class GoldEvent {
    object TogglePaidStatus : GoldEvent()
    data class UpdateTab(val tab: ZakatTab) : GoldEvent()
    data class UpdateRequirement1(val value: Boolean) : GoldEvent()
    data class UpdateRequirement2(val value: Boolean) : GoldEvent()
    data class UpdateRequirement3(val value: Boolean) : GoldEvent()
    data class UpdateGoldQuantity(val value: String) : GoldEvent()
    data class UpdateGoldPrice(val value: String) : GoldEvent()
    data class UpdateNisabType(@StringRes val value: Int) : GoldEvent()
    object Calculate : GoldEvent()
    object ToggleSummary : GoldEvent()
    object ResetCalculation : GoldEvent()
}
