package com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.state

import androidx.annotation.StringRes
import com.systematics.zakatcalculator.R
import com.systematics.zakatcalculator.presentation.screens.models.ZakatTab

private const val DEFAULT_PRICE_PER_UNIT = 200

enum class FitrahPayWith(@StringRes val labelRes: Int) {
    RICE(R.string.rice),
    MONEY(R.string.money)
}

enum class FitrahUnit(@StringRes val labelRes: Int) {
    KG(R.string.unit_kg),
    LITRE(R.string.unit_litre)
}

sealed class FitrahResult {
    data class Money(val amount: String) : FitrahResult()
    data class Rice(val amount: String, val unit: FitrahUnit) : FitrahResult()
}

data class FitrahState(
    val isPaid: Boolean = false,
    val canGiveRice: Boolean = false,
    val hasExcessFood: Boolean = false,
    val selectedTab: ZakatTab = ZakatTab.Calculator,
    val numberOfPeople: Int = 1,
    val payWith: FitrahPayWith = FitrahPayWith.RICE,
    val unit: FitrahUnit = FitrahUnit.KG,
    val pricePerUnit: String = DEFAULT_PRICE_PER_UNIT.toString(),
    val result: FitrahResult? = null,
    val showSummary: Boolean = false,
)
