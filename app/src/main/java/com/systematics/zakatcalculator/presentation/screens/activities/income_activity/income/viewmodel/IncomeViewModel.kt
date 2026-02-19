package com.systematics.zakatcalculator.presentation.screens.activities.income_activity.income.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.systematics.zakatcalculator.presentation.screens.activities.income_activity.income.events.IncomeEvent
import com.systematics.zakatcalculator.presentation.screens.activities.income_activity.income.state.IncomeCalculationResult
import com.systematics.zakatcalculator.presentation.screens.activities.income_activity.income.state.IncomeState
import com.systematics.zakatcalculator.utils.NumberFormatters
import com.systematics.zakatcalculator.utils.ZakatPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class IncomeViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = ZakatPreferences(application.applicationContext)
    private val _state = MutableStateFlow(loadState())
    val state = _state.asStateFlow()

    fun onEvent(event: IncomeEvent) {
        when (event) {
            is IncomeEvent.UpdateIncome -> _state.update { it.copy(income = event.income) }
            is IncomeEvent.UpdateExpense -> _state.update { it.copy(expense = event.expense) }
            is IncomeEvent.UpdateGoldPrice -> _state.update { it.copy(goldPrice = event.goldPrice) }
            is IncomeEvent.UpdateRequirement1 -> {
                _state.update { it.copy(requirement1 = event.value) }
                persistRequirements()
            }
            is IncomeEvent.UpdateRequirement2 -> {
                _state.update { it.copy(requirement2 = event.value) }
                persistRequirements()
            }
            is IncomeEvent.UpdateRequirement3 -> {
                _state.update { it.copy(requirement3 = event.value) }
                persistRequirements()
            }
            is IncomeEvent.UpdateRequirement4 -> {
                _state.update { it.copy(requirement4 = event.value) }
                persistRequirements()
            }
            is IncomeEvent.UpdateTab -> _state.update { it.copy(selectedTab = event.tab) }
            IncomeEvent.TogglePaidStatus -> {
                val current = _state.value
                if (!current.isPaid && isQualified(current)) {
                    _state.update { it.copy(isPaid = true) }
                    prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.INCOME_PREFIX, ZakatPreferences.KEY_PAID), true)
                }
            }
            IncomeEvent.Calculate -> {
                persistCalculationInputs()
                calculateZakat()
            }
            IncomeEvent.ToggleSummary -> _state.update { it.copy(showSummary = !it.showSummary) }
            IncomeEvent.ResetCalculation -> _state.update {
                it.copy(calculationResult = null, showSummary = false)
            }
        }
    }

    private fun calculateZakat() {
        val state = _state.value
        val income = state.income.toDoubleOrNull() ?: 0.0
        val expense = state.expense.toDoubleOrNull() ?: 0.0
        val goldPrice = state.goldPrice.toDoubleOrNull() ?: 0.0

        val netIncome = (income - expense).coerceAtLeast(0.0)
        val nisab = goldPrice * 85.0

        if (goldPrice > 0.0 && netIncome >= nisab) {
            val zakat = netIncome * 0.025
            _state.update {
                it.copy(
                    calculationResult = IncomeCalculationResult.Success(
                        amount = NumberFormatters.formatNoDecimals(zakat)
                    ),
                    showSummary = false
                )
            }
        } else {
            _state.update {
                it.copy(
                    calculationResult = IncomeCalculationResult.BelowNisab,
                    showSummary = false
                )
            }
        }
    }

    private fun persistRequirements() {
        val state = _state.value
        val qualified = isQualified(state)
        prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.INCOME_PREFIX, "requirement_1"), state.requirement1)
        prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.INCOME_PREFIX, "requirement_2"), state.requirement2)
        prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.INCOME_PREFIX, "requirement_3"), state.requirement3)
        prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.INCOME_PREFIX, "requirement_4"), state.requirement4)
        prefs.putBoolean(ZakatPreferences.key(ZakatPreferences.INCOME_PREFIX, ZakatPreferences.KEY_QUALIFIED), qualified)
    }

    private fun persistCalculationInputs() {
        val state = _state.value
        prefs.putString(ZakatPreferences.key(ZakatPreferences.INCOME_PREFIX, "income"), state.income)
        prefs.putString(ZakatPreferences.key(ZakatPreferences.INCOME_PREFIX, "expense"), state.expense)
        prefs.putString(ZakatPreferences.key(ZakatPreferences.INCOME_PREFIX, "gold_price"), state.goldPrice)
        prefs.putString(ZakatPreferences.COMMON_GOLD_PRICE_KEY, state.goldPrice)
    }

    private fun loadState(): IncomeState {
        val sharedGoldPrice = prefs.getString(ZakatPreferences.COMMON_GOLD_PRICE_KEY)
        val localGoldPrice = prefs.getString(ZakatPreferences.key(ZakatPreferences.INCOME_PREFIX, "gold_price"))
        val goldPrice = if (localGoldPrice.isNotBlank()) localGoldPrice else sharedGoldPrice

        return IncomeState(
            isPaid = prefs.getBoolean(ZakatPreferences.key(ZakatPreferences.INCOME_PREFIX, ZakatPreferences.KEY_PAID)),
            income = prefs.getString(ZakatPreferences.key(ZakatPreferences.INCOME_PREFIX, "income")),
            expense = prefs.getString(ZakatPreferences.key(ZakatPreferences.INCOME_PREFIX, "expense")),
            goldPrice = if (goldPrice.isNotBlank()) goldPrice else IncomeState().goldPrice,
            requirement1 = prefs.getBoolean(ZakatPreferences.key(ZakatPreferences.INCOME_PREFIX, "requirement_1")),
            requirement2 = prefs.getBoolean(ZakatPreferences.key(ZakatPreferences.INCOME_PREFIX, "requirement_2")),
            requirement3 = prefs.getBoolean(ZakatPreferences.key(ZakatPreferences.INCOME_PREFIX, "requirement_3")),
            requirement4 = prefs.getBoolean(ZakatPreferences.key(ZakatPreferences.INCOME_PREFIX, "requirement_4"))
        )
    }

    private fun isQualified(state: IncomeState): Boolean {
        return state.requirement1 && state.requirement2 && state.requirement3 && state.requirement4
    }
}
