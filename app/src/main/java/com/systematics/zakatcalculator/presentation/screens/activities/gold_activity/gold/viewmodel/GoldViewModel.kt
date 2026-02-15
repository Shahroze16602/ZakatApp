package com.systematics.zakatcalculator.presentation.screens.activities.gold_activity.gold.viewmodel

import androidx.lifecycle.ViewModel
import com.systematics.zakatcalculator.presentation.screens.activities.gold_activity.gold.events.GoldEvent
import com.systematics.zakatcalculator.presentation.screens.activities.gold_activity.gold.state.GoldCalculationResult
import com.systematics.zakatcalculator.presentation.screens.activities.gold_activity.gold.state.GoldState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GoldViewModel : ViewModel() {
    private val _state = MutableStateFlow(GoldState())
    val state: StateFlow<GoldState> = _state.asStateFlow()

    fun onEvent(event: GoldEvent) {
        when (event) {
            is GoldEvent.TogglePaidStatus -> {
                _state.update { it.copy(isPaid = !it.isPaid) }
            }
            is GoldEvent.UpdateTab -> {
                _state.update { it.copy(selectedTab = event.tab) }
            }
            is GoldEvent.UpdateRequirement1 -> {
                _state.update { it.copy(requirement1 = event.value) }
            }
            is GoldEvent.UpdateRequirement2 -> {
                _state.update { it.copy(requirement2 = event.value) }
            }
            is GoldEvent.UpdateRequirement3 -> {
                _state.update { it.copy(requirement3 = event.value) }
            }
            is GoldEvent.UpdateGoldQuantity -> {
                _state.update { it.copy(goldQuantity = event.value) }
            }
            is GoldEvent.UpdateGoldPrice -> {
                _state.update { it.copy(goldPrice = event.value) }
            }
            is GoldEvent.UpdateNisabType -> {
                _state.update { it.copy(nisabType = event.value) }
            }
            is GoldEvent.Calculate -> {
                calculateZakat()
            }
            is GoldEvent.ResetCalculation -> {
                _state.update { it.copy(calculationResult = null) }
            }
        }
    }

    private fun calculateZakat() {
        val state = _state.value
        val quantity = state.goldQuantity.toDoubleOrNull() ?: 0.0
        val price = state.goldPrice.toDoubleOrNull() ?: 0.0

        val nisabValue = if (state.nisabType == "87.48 gr") 87.48 else 85.0

        if (quantity >= nisabValue) {
            val zakatGrams = quantity * 0.025
            val zakatCash = zakatGrams * price
            _state.update { 
                it.copy(
                    calculationResult = GoldCalculationResult.Success(
                        grams = String.format("%.2f", zakatGrams),
                        cash = String.format("%,.0f", zakatCash)
                    )
                )
            }
        } else {
            _state.update { it.copy(calculationResult = GoldCalculationResult.BelowNisab) }
        }
    }
}
