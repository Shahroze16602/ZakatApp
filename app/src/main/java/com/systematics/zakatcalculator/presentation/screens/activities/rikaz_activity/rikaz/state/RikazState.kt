package com.systematics.zakatcalculator.presentation.screens.activities.rikaz_activity.rikaz.state

import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.state.FitrahTab

data class RikazState(
    val isPaid: Boolean = false,
    val selectedTab: FitrahTab = FitrahTab.Calculator,
    val treasureValue: String = "",
    val zakatAmount: String = "0"
)
