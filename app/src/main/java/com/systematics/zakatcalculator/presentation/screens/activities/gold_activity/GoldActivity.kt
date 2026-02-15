package com.systematics.zakatcalculator.presentation.screens.activities.gold_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.systematics.zakatcalculator.presentation.screens.activities.gold_activity.gold.GoldScreen
import com.systematics.zakatcalculator.ui.theme.ZakatCalculatorTheme

class GoldActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZakatCalculatorTheme {
                GoldScreen()
            }
        }
    }
}
