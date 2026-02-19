package com.systematics.zakatcalculator.presentation.screens.activities.gold_activity.gold.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.systematics.zakatcalculator.R
import com.systematics.zakatcalculator.presentation.screens.activities.gold_activity.gold.events.GoldEvent
import com.systematics.zakatcalculator.presentation.screens.activities.gold_activity.gold.state.GoldCalculationResult
import com.systematics.zakatcalculator.presentation.screens.activities.gold_activity.gold.state.GoldState
import com.systematics.zakatcalculator.utils.NumberFormatters
import com.systematics.zakatcalculator.utils.ZakatPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GoldViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = ZakatPreferences(application.applicationContext)
    private val _state = MutableStateFlow(loadState())
    val state: StateFlow<GoldState> = _state.asStateFlow()

    fun onEvent(event: GoldEvent) {
        when (event) {
            is GoldEvent.TogglePaidStatus -> {
                val current = _state.value
                if (!current.isPaid && isQualified(current)) {
                    _state.update { it.copy(isPaid = true) }
                    prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.GOLD_PREFIX, ZakatPreferences.KEY_PAID), true)
                }
            }
            is GoldEvent.UpdateTab -> {
                _state.update { it.copy(selectedTab = event.tab) }
            }
            is GoldEvent.UpdateRequirement1 -> {
                _state.update { it.copy(requirement1 = event.value) }
                persistRequirements()
            }
            is GoldEvent.UpdateRequirement2 -> {
                _state.update { it.copy(requirement2 = event.value) }
                persistRequirements()
            }
            is GoldEvent.UpdateRequirement3 -> {
                _state.update { it.copy(requirement3 = event.value) }
                persistRequirements()
            }
            is GoldEvent.UpdateGoldQuantity -> {
                _state.update { it.copy(goldQuantity = event.value) }
            }
            is GoldEvent.UpdateGoldPrice -> {
                _state.update { it.copy(goldPrice = event.value) }
            }
            is GoldEvent.UpdateNisabType -> {
                _state.update { it.copy(nisabTypeRes = event.value) }
            }
            is GoldEvent.Calculate -> {
                persistCalculationInputs()
                calculateZakat()
            }
            is GoldEvent.ToggleSummary -> {
                _state.update { it.copy(showSummary = !it.showSummary) }
            }
            is GoldEvent.ResetCalculation -> {
                _state.update { it.copy(calculationResult = null, showSummary = false) }
            }
        }
    }

    private fun calculateZakat() {
        val state = _state.value
        val quantity = state.goldQuantity.toDoubleOrNull() ?: 0.0
        val price = state.goldPrice.toDoubleOrNull() ?: 0.0
        val nisabValue = if (state.nisabTypeRes == R.string.gold_nisab_value_1) 87.48 else 85.0

        if (quantity >= nisabValue) {
            val zakatGrams = quantity * 0.025
            val zakatCash = zakatGrams * price
            _state.update {
                it.copy(
                    calculationResult = GoldCalculationResult.Success(
                        grams = NumberFormatters.formatTwoDecimals(zakatGrams),
                        cash = NumberFormatters.formatNoDecimals(zakatCash)
                    ),
                    showSummary = false
                )
            }
        } else {
            _state.update {
                it.copy(
                    calculationResult = GoldCalculationResult.BelowNisab,
                    showSummary = false
                )
            }
        }
    }

    private fun persistRequirements() {
        val state = _state.value
        val qualified = isQualified(state)
        prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.GOLD_PREFIX, "requirement_1"), state.requirement1)
        prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.GOLD_PREFIX, "requirement_2"), state.requirement2)
        prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.GOLD_PREFIX, "requirement_3"), state.requirement3)
        prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.GOLD_PREFIX, ZakatPreferences.KEY_QUALIFIED), qualified)
    }

    private fun persistCalculationInputs() {
        val state = _state.value
        prefs.putString(ZakatPreferences.key(ZakatPreferences.GOLD_PREFIX, "gold_quantity"), state.goldQuantity)
        prefs.putString(ZakatPreferences.key(ZakatPreferences.GOLD_PREFIX, "gold_price"), state.goldPrice)
        prefs.putString(ZakatPreferences.key(ZakatPreferences.GOLD_PREFIX, "nisab_type_res"), state.nisabTypeRes.toString())
        prefs.putString(ZakatPreferences.COMMON_GOLD_PRICE_KEY, state.goldPrice)
    }

    private fun loadState(): GoldState {
        val sharedGoldPrice = prefs.getString(ZakatPreferences.COMMON_GOLD_PRICE_KEY)
        val localGoldPrice = prefs.getString(ZakatPreferences.key(ZakatPreferences.GOLD_PREFIX, "gold_price"))
        val goldPrice = if (localGoldPrice.isNotBlank()) localGoldPrice else sharedGoldPrice

        return GoldState(
            isPaid = prefs.getBoolean(ZakatPreferences.key(ZakatPreferences.GOLD_PREFIX, ZakatPreferences.KEY_PAID)),
            requirement1 = prefs.getBoolean(ZakatPreferences.key(ZakatPreferences.GOLD_PREFIX, "requirement_1")),
            requirement2 = prefs.getBoolean(ZakatPreferences.key(ZakatPreferences.GOLD_PREFIX, "requirement_2")),
            requirement3 = prefs.getBoolean(ZakatPreferences.key(ZakatPreferences.GOLD_PREFIX, "requirement_3")),
            goldQuantity = prefs.getString(ZakatPreferences.key(ZakatPreferences.GOLD_PREFIX, "gold_quantity")),
            goldPrice = goldPrice,
            nisabTypeRes = prefs.getString(
                ZakatPreferences.key(ZakatPreferences.GOLD_PREFIX, "nisab_type_res"),
                R.string.gold_nisab_value_1.toString()
            ).toIntOrNull() ?: R.string.gold_nisab_value_1
        )
    }

    private fun isQualified(state: GoldState): Boolean {
        return state.requirement1 && state.requirement2 && state.requirement3
    }
}
