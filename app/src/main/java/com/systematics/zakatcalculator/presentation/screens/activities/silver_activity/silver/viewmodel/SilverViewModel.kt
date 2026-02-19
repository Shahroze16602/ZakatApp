package com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.silver.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.silver.events.SilverEvent
import com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.silver.state.SilverCalculationResult
import com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.silver.state.SilverState
import com.systematics.zakatcalculator.utils.NumberFormatters
import com.systematics.zakatcalculator.utils.ZakatPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SilverViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = ZakatPreferences(application.applicationContext)
    private val _state = MutableStateFlow(loadState())
    val state: StateFlow<SilverState> = _state.asStateFlow()

    fun onEvent(event: SilverEvent) {
        when (event) {
            is SilverEvent.TogglePaidStatus -> {
                val current = _state.value
                if (!current.isPaid && isQualified(current)) {
                    _state.update { it.copy(isPaid = true) }
                    prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.SILVER_PREFIX, ZakatPreferences.KEY_PAID), true)
                }
            }
            is SilverEvent.UpdateTab -> {
                _state.update { it.copy(selectedTab = event.tab) }
            }
            is SilverEvent.UpdateRequirement1 -> {
                _state.update { it.copy(requirement1 = event.value) }
                persistRequirements()
            }
            is SilverEvent.UpdateRequirement2 -> {
                _state.update { it.copy(requirement2 = event.value) }
                persistRequirements()
            }
            is SilverEvent.UpdateRequirement3 -> {
                _state.update { it.copy(requirement3 = event.value) }
                persistRequirements()
            }
            is SilverEvent.UpdateSilverQuantity -> {
                _state.update { it.copy(silverQuantity = event.value) }
            }
            is SilverEvent.UpdateSilverPrice -> {
                _state.update { it.copy(silverPrice = event.value) }
            }
            is SilverEvent.Calculate -> {
                persistCalculationInputs()
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

    private fun persistRequirements() {
        val state = _state.value
        val qualified = isQualified(state)
        prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.SILVER_PREFIX, "requirement_1"), state.requirement1)
        prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.SILVER_PREFIX, "requirement_2"), state.requirement2)
        prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.SILVER_PREFIX, "requirement_3"), state.requirement3)
        prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.SILVER_PREFIX, ZakatPreferences.KEY_QUALIFIED), qualified)
    }

    private fun persistCalculationInputs() {
        val state = _state.value
        prefs.putString(ZakatPreferences.key(ZakatPreferences.SILVER_PREFIX, "silver_quantity"), state.silverQuantity)
        prefs.putString(ZakatPreferences.key(ZakatPreferences.SILVER_PREFIX, "silver_price"), state.silverPrice)
    }

    private fun loadState(): SilverState {
        return SilverState(
            isPaid = prefs.getBoolean(ZakatPreferences.key(ZakatPreferences.SILVER_PREFIX, ZakatPreferences.KEY_PAID)),
            requirement1 = prefs.getBoolean(ZakatPreferences.key(ZakatPreferences.SILVER_PREFIX, "requirement_1")),
            requirement2 = prefs.getBoolean(ZakatPreferences.key(ZakatPreferences.SILVER_PREFIX, "requirement_2")),
            requirement3 = prefs.getBoolean(ZakatPreferences.key(ZakatPreferences.SILVER_PREFIX, "requirement_3")),
            silverQuantity = prefs.getString(ZakatPreferences.key(ZakatPreferences.SILVER_PREFIX, "silver_quantity")),
            silverPrice = prefs.getString(ZakatPreferences.key(ZakatPreferences.SILVER_PREFIX, "silver_price"))
        )
    }

    private fun isQualified(state: SilverState): Boolean {
        return state.requirement1 && state.requirement2 && state.requirement3
    }
}
