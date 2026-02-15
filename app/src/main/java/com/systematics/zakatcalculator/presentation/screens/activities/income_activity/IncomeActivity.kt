package com.systematics.zakatcalculator.presentation.screens.activities.income_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.systematics.zakatcalculator.presentation.screens.activities.income_activity.income.IncomeScreen
import com.systematics.zakatcalculator.ui.theme.ZakatCalculatorTheme

class IncomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZakatCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    IncomeScreen()
                }
            }
        }
    }
}
