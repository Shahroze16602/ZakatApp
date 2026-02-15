package com.systematics.zakatcalculator.presentation.screens.activities.rikaz_activity.rikaz.viewmodel

import androidx.lifecycle.ViewModel
import com.systematics.zakatcalculator.presentation.screens.activities.rikaz_activity.rikaz.events.RikazEvent
import com.systematics.zakatcalculator.presentation.screens.activities.rikaz_activity.rikaz.state.RikazState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RikazViewModel : ViewModel() {

    private val _state = MutableStateFlow(RikazState())
    val state = _state.asStateFlow()

    fun onEvent(event: RikazEvent) {
        when (event) {
            is RikazEvent.UpdateTreasureValue -> _state.update { it.copy(treasureValue = event.value) }
            is RikazEvent.UpdateTab -> _state.update { it.copy(selectedTab = event.tab) }
            RikazEvent.TogglePaidStatus -> _state.update { it.copy(isPaid = !it.isPaid) }
            RikazEvent.CalculateZakat -> {
                val treasureValue = _state.value.treasureValue.toDoubleOrNull() ?: 0.0
                val zakatAmount = treasureValue * 0.20
                _state.update { it.copy(zakatAmount = zakatAmount.toString()) }
            }
        }
    }
}
