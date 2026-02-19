package com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.events.FitrahEvent
import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.state.FitrahPayWith
import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.state.FitrahResult
import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.state.FitrahState
import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.state.FitrahUnit
import com.systematics.zakatcalculator.utils.NumberFormatters
import com.systematics.zakatcalculator.utils.ZakatPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FitrahViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = ZakatPreferences(application.applicationContext)
    private val _state = MutableStateFlow(loadState())
    val state: StateFlow<FitrahState> = _state.asStateFlow()

    fun onEvent(event: FitrahEvent) {
        when (event) {
            is FitrahEvent.TogglePaidStatus -> {
                val current = _state.value
                val qualified = current.canGiveRice && current.hasExcessFood
                if (!current.isPaid && qualified) {
                    _state.update { it.copy(isPaid = true) }
                    prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.FITRAH_PREFIX, ZakatPreferences.KEY_PAID), true)
                }
            }
            is FitrahEvent.UpdateCanGiveRice -> {
                _state.update { it.copy(canGiveRice = event.value) }
                persistRequirements()
            }
            is FitrahEvent.UpdateHasExcessFood -> {
                _state.update { it.copy(hasExcessFood = event.value) }
                persistRequirements()
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
                persistCalculationInputs()
                calculateZakat()
            }
            is FitrahEvent.ToggleSummary -> {
                _state.update { it.copy(showSummary = !it.showSummary) }
            }
            is FitrahEvent.Reset -> {
                _state.update { it.copy(result = null, showSummary = false) }
            }
        }
    }

    private fun calculateZakat() {
        val state = _state.value
        val multiplier = if (state.unit == FitrahUnit.LITRE) 3.5 else 2.5
        val people = state.numberOfPeople
        val amountOfRice = people * multiplier

        val result = if (state.payWith == FitrahPayWith.MONEY) {
            val price = state.pricePerUnit.toDoubleOrNull() ?: 0.0
            val totalPrice = amountOfRice * price
            FitrahResult.Money(NumberFormatters.formatTwoDecimals(totalPrice))
        } else {
            FitrahResult.Rice(
                amount = NumberFormatters.formatOneDecimal(amountOfRice),
                unit = state.unit
            )
        }

        _state.update { it.copy(result = result, showSummary = false) }
    }

    private fun persistRequirements() {
        val state = _state.value
        val qualified = state.canGiveRice && state.hasExcessFood
        prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.FITRAH_PREFIX, "can_give_rice"), state.canGiveRice)
        prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.FITRAH_PREFIX, "has_excess_food"), state.hasExcessFood)
        prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.FITRAH_PREFIX, ZakatPreferences.KEY_QUALIFIED), qualified)
    }

    private fun persistCalculationInputs() {
        val state = _state.value
        prefs.putString(ZakatPreferences.key(ZakatPreferences.FITRAH_PREFIX, "number_of_people"), state.numberOfPeople.toString())
        prefs.putString(ZakatPreferences.key(ZakatPreferences.FITRAH_PREFIX, "pay_with"), state.payWith.name)
        prefs.putString(ZakatPreferences.key(ZakatPreferences.FITRAH_PREFIX, "unit"), state.unit.name)
        prefs.putString(ZakatPreferences.key(ZakatPreferences.FITRAH_PREFIX, "price_per_unit"), state.pricePerUnit)
    }

    private fun loadState(): FitrahState {
        val canGiveRice = prefs.getBoolean(ZakatPreferences.key(ZakatPreferences.FITRAH_PREFIX, "can_give_rice"))
        val hasExcessFood = prefs.getBoolean(ZakatPreferences.key(ZakatPreferences.FITRAH_PREFIX, "has_excess_food"))
        val payWith = prefs.getString(ZakatPreferences.key(ZakatPreferences.FITRAH_PREFIX, "pay_with"), FitrahPayWith.RICE.name)
            .let { runCatching { FitrahPayWith.valueOf(it) }.getOrDefault(FitrahPayWith.RICE) }
        val unit = prefs.getString(ZakatPreferences.key(ZakatPreferences.FITRAH_PREFIX, "unit"), FitrahUnit.KG.name)
            .let { runCatching { FitrahUnit.valueOf(it) }.getOrDefault(FitrahUnit.KG) }

        return FitrahState(
            isPaid = prefs.getBoolean(ZakatPreferences.key(ZakatPreferences.FITRAH_PREFIX, ZakatPreferences.KEY_PAID)),
            canGiveRice = canGiveRice,
            hasExcessFood = hasExcessFood,
            numberOfPeople = prefs.getString(
                ZakatPreferences.key(ZakatPreferences.FITRAH_PREFIX, "number_of_people"),
                "1"
            ).toIntOrNull() ?: 1,
            payWith = payWith,
            unit = unit,
            pricePerUnit = prefs.getString(
                ZakatPreferences.key(ZakatPreferences.FITRAH_PREFIX, "price_per_unit"),
                FitrahState().pricePerUnit
            )
        )
    }
}
