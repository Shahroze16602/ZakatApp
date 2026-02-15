package com.systematics.zakatcalculator.presentation.screens.activities.silver_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.silver.SilverScreen
import com.systematics.zakatcalculator.ui.theme.ZakatCalculatorTheme

class SilverActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZakatCalculatorTheme {
                SilverScreen()
            }
        }
    }
}
