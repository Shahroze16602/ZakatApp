package com.systematics.zakatcalculator.presentation.screens.activities.splash_activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.systematics.zakatcalculator.presentation.screens.activities.home_activity.HomeActivity
import com.systematics.zakatcalculator.presentation.screens.activities.splash_activity.splash.SplashScreen
import com.systematics.zakatcalculator.ui.theme.ZakatCalculatorTheme

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZakatCalculatorTheme {
                SplashScreen(
                    onNavigateHome = {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}
