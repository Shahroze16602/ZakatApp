package com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.FitrahScreen
import com.systematics.zakatcalculator.ui.theme.ZakatCalculatorTheme

class FitrahActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZakatCalculatorTheme {
                FitrahScreen()
            }
        }
    }
}