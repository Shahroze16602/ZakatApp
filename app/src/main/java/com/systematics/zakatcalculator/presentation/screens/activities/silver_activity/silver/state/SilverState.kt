package com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.silver.state

data class SilverState(
    val isPaid: Boolean = false,
    val selectedTab: SilverTab = SilverTab.Calculator,
    // Requirements
    val requirement1: Boolean = false,
    val requirement2: Boolean = false,
    val requirement3: Boolean = false,
    // Calculator
    val silverQuantity: String = "",
    val silverPrice: String = "",
    val nisabType: String = "612.36 gr", // Standard Silver Nisab
    val calculationResult: SilverCalculationResult? = null
)

sealed class SilverCalculationResult {
    data class Success(val grams: String, val cash: String) : SilverCalculationResult()
    object BelowNisab : SilverCalculationResult()
}

enum class SilverTab {
    Calculator, ZakatInfo
}
