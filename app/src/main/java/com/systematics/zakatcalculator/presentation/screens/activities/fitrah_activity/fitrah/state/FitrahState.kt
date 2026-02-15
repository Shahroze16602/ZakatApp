package com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.state

data class FitrahState(
    val isPaid: Boolean = false,
    val canGiveRice: Boolean = false,
    val hasExcessFood: Boolean = false,
    val selectedTab: FitrahTab = FitrahTab.Calculator,
    val numberOfPeople: Int = 1,
    val payWith: String = "Rice",
    val unit: String = "Kg",
    val pricePerUnit: String = "200",
    val result: String? = null,
    val showSummary: Boolean = false,
)

enum class FitrahTab {
    Calculator, ZakatInfo
}
