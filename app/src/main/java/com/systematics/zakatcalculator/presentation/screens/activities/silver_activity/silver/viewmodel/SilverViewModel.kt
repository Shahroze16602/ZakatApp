package com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.silver.viewmodel

import androidx.lifecycle.ViewModel
import com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.silver.events.SilverEvent
import com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.silver.state.SilverCalculationResult
import com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.silver.state.SilverState
import com.systematics.zakatcalculator.utils.NumberFormatters
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SilverViewModel : ViewModel() {
    private val _state = MutableStateFlow(SilverState())
    val state: StateFlow<SilverState> = _state.asStateFlow()

    fun onEvent(event: SilverEvent) {
        when (event) {
            is SilverEvent.TogglePaidStatus -> {
                _state.update { it.copy(isPaid = !it.isPaid) }
            }
            is SilverEvent.UpdateTab -> {
                _state.update { it.copy(selectedTab = event.tab) }
            }
            is SilverEvent.UpdateRequirement1 -> {
                _state.update { it.copy(requirement1 = event.value) }
            }
            is SilverEvent.UpdateRequirement2 -> {
                _state.update { it.copy(requirement2 = event.value) }
            }
            is SilverEvent.UpdateRequirement3 -> {
                _state.update { it.copy(requirement3 = event.value) }
            }
            is SilverEvent.UpdateSilverQuantity -> {
                _state.update { it.copy(silverQuantity = event.value) }
            }
            is SilverEvent.UpdateSilverPrice -> {
                _state.update { it.copy(silverPrice = event.value) }
            }
            is SilverEvent.Calculate -> {
                calculateZakat()
            }
            is SilverEvent.ToggleSummary -> {
                _state.update { it.copy(showSummary = !it.showSummary) }
            }
            is SilverEvent.ResetCalculation -> {
                _state.update { it.copy(calculationResult = null, showSummary = false) }
            }
        }
    }

    private fun calculateZakat() {
        val state = _state.value
        val quantity = state.silverQuantity.toDoubleOrNull() ?: 0.0
        val price = state.silverPrice.toDoubleOrNull() ?: 0.0

        val nisabValue = 612.36

        if (quantity >= nisabValue) {
            val zakatGrams = quantity * 0.025
            val zakatCash = zakatGrams * price
            _state.update { 
                it.copy(
                    calculationResult = SilverCalculationResult.Success(
                        grams = NumberFormatters.formatTwoDecimals(zakatGrams),
                        cash = NumberFormatters.formatNoDecimals(zakatCash)
                    ),
                    showSummary = false
                )
            }
        } else {
            _state.update {
                it.copy(
                    calculationResult = SilverCalculationResult.BelowNisab,
                    showSummary = false
                )
            }
        }
    }
}
