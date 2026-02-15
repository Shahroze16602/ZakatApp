package com.systematics.zakatcalculator.presentation.screens.activities.gold_activity.gold.state

data class GoldState(
    val isPaid: Boolean = false,
    val selectedTab: GoldTab = GoldTab.Calculator,
    // Requirements
    val requirement1: Boolean = false,
    val requirement2: Boolean = false,
    val requirement3: Boolean = false,
    // Calculator
    val goldQuantity: String = "",
    val goldPrice: String = "",
    val nisabType: String = "87.48 gr", // Toggle in UI: 87.48 or 85.0
    val calculationResult: GoldCalculationResult? = null
)

sealed class GoldCalculationResult {
    data class Success(val grams: String, val cash: String) : GoldCalculationResult()
    object BelowNisab : GoldCalculationResult()
}

enum class GoldTab {
    Calculator, ZakatInfo
}
