package com.systematics.zakatcalculator.presentation.screens.activities.savings_activity.savings.viewmodel

import androidx.lifecycle.ViewModel
import com.systematics.zakatcalculator.presentation.screens.activities.savings_activity.savings.events.SavingsEvent
import com.systematics.zakatcalculator.presentation.screens.activities.savings_activity.savings.state.SavingsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SavingsViewModel : ViewModel() {

    private val _state = MutableStateFlow(SavingsState())
    val state = _state.asStateFlow()

    fun onEvent(event: SavingsEvent) {
        when (event) {
            is SavingsEvent.UpdateSavings -> _state.update { it.copy(savings = event.savings) }
            is SavingsEvent.UpdateInterests -> _state.update { it.copy(interests = event.interests) }
            is SavingsEvent.UpdateGoldPrice -> _state.update { it.copy(goldPrice = event.goldPrice) }
            is SavingsEvent.UpdateRequirement1 -> _state.update { it.copy(requirement1 = event.value) }
            is SavingsEvent.UpdateRequirement2 -> _state.update { it.copy(requirement2 = event.value) }
            is SavingsEvent.UpdateRequirement3 -> _state.update { it.copy(requirement3 = event.value) }
            is SavingsEvent.UpdateTab -> _state.update { it.copy(selectedTab = event.tab) }
            SavingsEvent.TogglePaidStatus -> _state.update { it.copy(isPaid = !it.isPaid) }
        }
    }
}
