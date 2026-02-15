package com.systematics.zakatcalculator.presentation.screens.activities.income_activity.income.viewmodel

import androidx.lifecycle.ViewModel
import com.systematics.zakatcalculator.presentation.screens.activities.income_activity.income.events.IncomeEvent
import com.systematics.zakatcalculator.presentation.screens.activities.income_activity.income.state.IncomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class IncomeViewModel : ViewModel() {

    private val _state = MutableStateFlow(IncomeState())
    val state = _state.asStateFlow()

    fun onEvent(event: IncomeEvent) {
        when (event) {
            is IncomeEvent.UpdateIncome -> _state.update { it.copy(income = event.income) }
            is IncomeEvent.UpdateExpense -> _state.update { it.copy(expense = event.expense) }
            is IncomeEvent.UpdateGoldPrice -> _state.update { it.copy(goldPrice = event.goldPrice) }
            is IncomeEvent.UpdateRequirement1 -> _state.update { it.copy(requirement1 = event.value) }
            is IncomeEvent.UpdateRequirement2 -> _state.update { it.copy(requirement2 = event.value) }
            is IncomeEvent.UpdateRequirement3 -> _state.update { it.copy(requirement3 = event.value) }
            is IncomeEvent.UpdateRequirement4 -> _state.update { it.copy(requirement4 = event.value) }
            is IncomeEvent.UpdateTab -> _state.update { it.copy(selectedTab = event.tab) }
            IncomeEvent.TogglePaidStatus -> _state.update { it.copy(isPaid = !it.isPaid) }
        }
    }
}
