package com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.viewmodel

import androidx.lifecycle.ViewModel
import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.events.FitrahEvent
import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.state.FitrahState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FitrahViewModel : ViewModel() {
    private val _state = MutableStateFlow(FitrahState())
    val state: StateFlow<FitrahState> = _state.asStateFlow()

    fun onEvent(event: FitrahEvent) {
        when (event) {
            is FitrahEvent.TogglePaidStatus -> {
                _state.update { it.copy(isPaid = !it.isPaid) }
            }
            is FitrahEvent.UpdateCanGiveRice -> {
                _state.update { it.copy(canGiveRice = event.value) }
            }
            is FitrahEvent.UpdateHasExcessFood -> {
                _state.update { it.copy(hasExcessFood = event.value) }
            }
            is FitrahEvent.UpdateTab -> {
                _state.update { it.copy(selectedTab = event.tab) }
            }
            is FitrahEvent.IncrementPeople -> {
                _state.update { it.copy(numberOfPeople = it.numberOfPeople + 1) }
            }
            is FitrahEvent.DecrementPeople -> {
                _state.update {
                    if (it.numberOfPeople > 1) it.copy(numberOfPeople = it.numberOfPeople - 1)
                    else it
                }
            }
            is FitrahEvent.UpdatePayWith -> {
                _state.update { it.copy(payWith = event.value) }
            }
            is FitrahEvent.UpdateUnit -> {
                _state.update { it.copy(unit = event.value) }
            }
            is FitrahEvent.UpdatePrice -> {
                _state.update { it.copy(pricePerUnit = event.value) }
            }
            is FitrahEvent.Calculate -> {
                calculateZakat()
            }
            is FitrahEvent.ToggleSummary -> {
                _state.update { it.copy(showSummary = !it.showSummary) }
            }
            is FitrahEvent.Reset -> {
                _state.value = FitrahState()
            }
        }
    }

    private fun calculateZakat() {
        val state = _state.value
        val multiplier = if (state.unit.equals("Litre", ignoreCase = true)) 3.5 else 2.5
        val people = state.numberOfPeople
        val amountOfRice = people * multiplier
        
        val resultString = if (state.payWith.equals("Money", ignoreCase = true)) {
            val price = state.pricePerUnit.toDoubleOrNull() ?: 0.0
            val totalPrice = amountOfRice * price
            String.format("%.2f", totalPrice)
        } else {
            String.format("%.1f %s", amountOfRice, state.unit)
        }
        
        _state.update { it.copy(result = resultString) }
    }
}