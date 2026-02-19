package com.systematics.zakatcalculator.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class ZakatPreferences(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return prefs.getBoolean(key, defaultValue)
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return prefs.getString(key, defaultValue) ?: defaultValue
    }

    fun putBoolean(key: String, value: Boolean) {
        prefs.edit { putBoolean(key, value) }
    }

    fun putString(key: String, value: String) {
        prefs.edit { putString(key, value) }
    }

    companion object {
        const val PREFS_NAME = "zakat_preferences"
        const val COMMON_GOLD_PRICE_KEY = "common_gold_price"

        const val FITRAH_PREFIX = "fitrah"
        const val GOLD_PREFIX = "gold"
        const val SILVER_PREFIX = "silver"
        const val SAVINGS_PREFIX = "savings"
        const val INCOME_PREFIX = "income"
        const val RIKAZ_PREFIX = "rikaz"

        const val KEY_PAID = "paid"
        const val KEY_QUALIFIED = "qualified"

        fun key(prefix: String, suffix: String): String = "${prefix}_$suffix"
    }
}
