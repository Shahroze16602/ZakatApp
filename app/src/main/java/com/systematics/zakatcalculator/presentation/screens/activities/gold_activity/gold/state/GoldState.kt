package com.systematics.zakatcalculator.presentation.screens.activities.gold_activity.gold.state

import com.systematics.zakatcalculator.presentation.screens.models.ZakatTab

data class GoldState(
    val isPaid: Boolean = false,
    val selectedTab: ZakatTab = ZakatTab.Calculator,
    // Requirements
    val requirement1: Boolean = false,
    val requirement2: Boolean = false,
    val requirement3: Boolean = false,
    // Calculator
    val goldQuantity: String = "",
    val goldPrice: String = "",
    val nisabType: String = "87.48 gr", // Toggle in UI: 87.48 or 85.0
    val calculationResult: GoldCalculationResult? = null,
    val showSummary: Boolean = false
)

sealed class GoldCalculationResult {
    data class Success(val grams: String, val cash: String) : GoldCalculationResult()
    object BelowNisab : GoldCalculationResult()
}
