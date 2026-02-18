package com.systematics.zakatcalculator.utils

import java.text.NumberFormat
import java.util.Locale

object NumberFormatters {
    private val twoDecimals: NumberFormat = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
        isGroupingUsed = true
    }

    private val oneDecimal: NumberFormat = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 1
        maximumFractionDigits = 1
        isGroupingUsed = true
    }

    private val noDecimals: NumberFormat = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 0
        maximumFractionDigits = 0
        isGroupingUsed = true
    }

    fun formatTwoDecimals(value: Double): String = twoDecimals.format(value)

    fun formatOneDecimal(value: Double): String = oneDecimal.format(value)

    fun formatNoDecimals(value: Double): String = noDecimals.format(value)
}
