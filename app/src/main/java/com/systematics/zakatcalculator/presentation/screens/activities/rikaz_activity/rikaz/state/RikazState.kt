package com.systematics.zakatcalculator.presentation.screens.activities.rikaz_activity.rikaz.state

import com.systematics.zakatcalculator.presentation.screens.models.ZakatTab

data class RikazState(
    val isPaid: Boolean = false,
    val selectedTab: ZakatTab = ZakatTab.Calculator,
    val requirement1: Boolean = false,
    val treasureValue: String = "",
    val zakatAmount: String? = null,
    val showSummary: Boolean = false
)
