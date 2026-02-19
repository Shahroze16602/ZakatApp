package com.systematics.zakatcalculator.presentation.screens.activities.savings_activity.savings.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.systematics.zakatcalculator.presentation.screens.activities.savings_activity.savings.events.SavingsEvent
import com.systematics.zakatcalculator.presentation.screens.activities.savings_activity.savings.state.SavingsCalculationResult
import com.systematics.zakatcalculator.presentation.screens.activities.savings_activity.savings.state.SavingsState
import com.systematics.zakatcalculator.utils.NumberFormatters
import com.systematics.zakatcalculator.utils.ZakatPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SavingsViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = ZakatPreferences(application.applicationContext)
    private val _state = MutableStateFlow(loadState())
    val state = _state.asStateFlow()

    fun onEvent(event: SavingsEvent) {
        when (event) {
            is SavingsEvent.UpdateSavings -> _state.update { it.copy(savings = event.savings) }
            is SavingsEvent.UpdateInterests -> _state.update { it.copy(interests = event.interests) }
            is SavingsEvent.UpdateGoldPrice -> _state.update { it.copy(goldPrice = event.goldPrice) }
            is SavingsEvent.UpdateRequirement1 -> {
                _state.update { it.copy(requirement1 = event.value) }
                persistRequirements()
            }
            is SavingsEvent.UpdateRequirement2 -> {
                _state.update { it.copy(requirement2 = event.value) }
                persistRequirements()
            }
            is SavingsEvent.UpdateRequirement3 -> {
                _state.update { it.copy(requirement3 = event.value) }
                persistRequirements()
            }
            is SavingsEvent.UpdateTab -> _state.update { it.copy(selectedTab = event.tab) }
            SavingsEvent.TogglePaidStatus -> {
                val current = _state.value
                if (!current.isPaid && isQualified(current)) {
                    _state.update { it.copy(isPaid = true) }
                    prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.SAVINGS_PREFIX, ZakatPreferences.KEY_PAID), true)
                }
            }
            SavingsEvent.Calculate -> {
                persistCalculationInputs()
                calculateZakat()
            }
            SavingsEvent.ToggleSummary -> _state.update { it.copy(showSummary = !it.showSummary) }
            SavingsEvent.ResetCalculation -> _state.update {
                it.copy(calculationResult = null, showSummary = false)
            }
        }
    }

    private fun calculateZakat() {
        val state = _state.value
        val savings = state.savings.toDoubleOrNull() ?: 0.0
        val interests = state.interests.toDoubleOrNull() ?: 0.0
        val goldPrice = state.goldPrice.toDoubleOrNull() ?: 0.0

        val netSavings = (savings - interests).coerceAtLeast(0.0)
        val nisab = goldPrice * 85.0

        if (goldPrice > 0.0 && netSavings >= nisab) {
            val zakat = netSavings * 0.025
            _state.update {
                it.copy(
                    calculationResult = SavingsCalculationResult.Success(
                        amount = NumberFormatters.formatNoDecimals(zakat)
                    ),
                    showSummary = false
                )
            }
        } else {
            _state.update {
                it.copy(
                    calculationResult = SavingsCalculationResult.BelowNisab,
                    showSummary = false
                )
            }
        }
    }

    private fun persistRequirements() {
        val state = _state.value
        val qualified = isQualified(state)
        prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.SAVINGS_PREFIX, "requirement_1"), state.requirement1)
        prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.SAVINGS_PREFIX, "requirement_2"), state.requirement2)
        prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.SAVINGS_PREFIX, "requirement_3"), state.requirement3)
        prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.SAVINGS_PREFIX, ZakatPreferences.KEY_QUALIFIED), qualified)
    }

    private fun persistCalculationInputs() {
        val state = _state.value
        prefs.putString(ZakatPreferences.key(ZakatPreferences.SAVINGS_PREFIX, "savings"), state.savings)
        prefs.putString(ZakatPreferences.key(ZakatPreferences.SAVINGS_PREFIX, "interests"), state.interests)
        prefs.putString(ZakatPreferences.key(ZakatPreferences.SAVINGS_PREFIX, "gold_price"), state.goldPrice)
        prefs.putString(ZakatPreferences.COMMON_GOLD_PRICE_KEY, state.goldPrice)
    }

    private fun loadState(): SavingsState {
        val sharedGoldPrice = prefs.getString(ZakatPreferences.COMMON_GOLD_PRICE_KEY)
        val localGoldPrice = prefs.getString(ZakatPreferences.key(ZakatPreferences.SAVINGS_PREFIX, "gold_price"))
        val goldPrice = if (localGoldPrice.isNotBlank()) localGoldPrice else sharedGoldPrice

        return SavingsState(
            isPaid = prefs.getBoolean(ZakatPreferences.key(ZakatPreferences.SAVINGS_PREFIX, ZakatPreferences.KEY_PAID)),
            savings = prefs.getString(ZakatPreferences.key(ZakatPreferences.SAVINGS_PREFIX, "savings")),
            interests = prefs.getString(ZakatPreferences.key(ZakatPreferences.SAVINGS_PREFIX, "interests")),
            goldPrice = goldPrice,
            requirement1 = prefs.getBoolean(ZakatPreferences.key(ZakatPreferences.SAVINGS_PREFIX, "requirement_1")),
            requirement2 = prefs.getBoolean(ZakatPreferences.key(ZakatPreferences.SAVINGS_PREFIX, "requirement_2")),
            requirement3 = prefs.getBoolean(ZakatPreferences.key(ZakatPreferences.SAVINGS_PREFIX, "requirement_3"))
        )
    }

    private fun isQualified(state: SavingsState): Boolean {
        return state.requirement1 && state.requirement2 && state.requirement3
    }
}
