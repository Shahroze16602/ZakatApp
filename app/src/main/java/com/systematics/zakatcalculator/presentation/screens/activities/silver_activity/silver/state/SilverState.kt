package com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.silver.state

import androidx.annotation.StringRes
import com.systematics.zakatcalculator.R
import com.systematics.zakatcalculator.presentation.screens.models.ZakatTab

data class SilverState(
    val isPaid: Boolean = false,
    val selectedTab: ZakatTab = ZakatTab.Calculator,
    // Requirements
    val requirement1: Boolean = false,
    val requirement2: Boolean = false,
    val requirement3: Boolean = false,
    // Calculator
    val silverQuantity: String = "",
    val silverPrice: String = "",
    @StringRes val nisabTypeRes: Int = R.string.silver_nisab_value, // Standard Silver Nisab
    val calculationResult: SilverCalculationResult? = null,
    val showSummary: Boolean = false
)

sealed class SilverCalculationResult {
    data class Success(val grams: String, val cash: String) : SilverCalculationResult()
    object BelowNisab : SilverCalculationResult()
}
