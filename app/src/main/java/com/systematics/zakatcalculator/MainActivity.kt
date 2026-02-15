package com.systematics.zakatcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.systematics.zakatcalculator.presentation.screens.activities.home_activity.home.HomeScreen
import com.systematics.zakatcalculator.ui.theme.ZakatCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZakatCalculatorTheme {
                HomeScreen()
            }
        }
    }
}