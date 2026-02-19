package com.systematics.zakatcalculator.presentation.screens.activities.rikaz_activity.rikaz.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.systematics.zakatcalculator.presentation.screens.activities.rikaz_activity.rikaz.events.RikazEvent
import com.systematics.zakatcalculator.presentation.screens.activities.rikaz_activity.rikaz.state.RikazState
import com.systematics.zakatcalculator.utils.NumberFormatters
import com.systematics.zakatcalculator.utils.ZakatPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RikazViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = ZakatPreferences(application.applicationContext)
    private val _state = MutableStateFlow(loadState())
    val state = _state.asStateFlow()

    fun onEvent(event: RikazEvent) {
        when (event) {
            is RikazEvent.UpdateTreasureValue -> _state.update { it.copy(treasureValue = event.value) }
            is RikazEvent.UpdateRequirement1 -> {
                _state.update { it.copy(requirement1 = event.value) }
                persistRequirements()
            }
            is RikazEvent.UpdateTab -> _state.update { it.copy(selectedTab = event.tab) }
            RikazEvent.TogglePaidStatus -> {
                val current = _state.value
                if (!current.isPaid && current.requirement1) {
                    _state.update { it.copy(isPaid = true) }
                    prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.RIKAZ_PREFIX, ZakatPreferences.KEY_PAID), true)
                }
            }
            RikazEvent.ToggleSummary -> _state.update { it.copy(showSummary = !it.showSummary) }
            RikazEvent.ResetCalculation -> _state.update {
                it.copy(zakatAmount = null, showSummary = false)
            }
            RikazEvent.CalculateZakat -> {
                persistCalculationInputs()
                val treasureValue = _state.value.treasureValue.toDoubleOrNull() ?: 0.0
                val zakatAmount = treasureValue * 0.20
                _state.update {
                    it.copy(
                        zakatAmount = NumberFormatters.formatNoDecimals(zakatAmount),
                        showSummary = false
                    )
                }
            }
        }
    }

    private fun persistRequirements() {
        val state = _state.value
        prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.RIKAZ_PREFIX, "requirement_1"), state.requirement1)
        prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.RIKAZ_PREFIX, ZakatPreferences.KEY_QUALIFIED), state.requirement1)
    }

    private fun persistCalculationInputs() {
        prefs.putString(
            ZakatPreferences.key(ZakatPreferences.RIKAZ_PREFIX, "treasure_value"),
            _state.value.treasureValue
        )
    }

    private fun loadState(): RikazState {
        return RikazState(
            isPaid = prefs.getBoolean(ZakatPreferences.key(ZakatPreferences.RIKAZ_PREFIX, ZakatPreferences.KEY_PAID)),
            requirement1 = prefs.getBoolean(ZakatPreferences.key(ZakatPreferences.RIKAZ_PREFIX, "requirement_1")),
            treasureValue = prefs.getString(ZakatPreferences.key(ZakatPreferences.RIKAZ_PREFIX, "treasure_value"))
        )
    }
}
