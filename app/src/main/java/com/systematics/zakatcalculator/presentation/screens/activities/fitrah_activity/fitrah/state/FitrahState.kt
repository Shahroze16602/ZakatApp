package com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.state

import com.systematics.zakatcalculator.presentation.screens.models.ZakatTab

data class FitrahState(
    val isPaid: Boolean = false,
    val canGiveRice: Boolean = false,
    val hasExcessFood: Boolean = false,
    val selectedTab: ZakatTab = ZakatTab.Calculator,
    val numberOfPeople: Int = 1,
    val payWith: String = "Rice",
    val unit: String = "Kg",
    val pricePerUnit: String = "200",
    val result: String? = null,
    val showSummary: Boolean = false,
)
