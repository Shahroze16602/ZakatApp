package com.systematics.zakatcalculator.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.systematics.zakatcalculator.R

private val DmSansFontFamily = FontFamily(
    Font(R.font.dm_sans_regular, FontWeight.Normal),
    Font(R.font.dm_sans_medium, FontWeight.Medium),
    Font(R.font.dm_sans_semibold, FontWeight.SemiBold),
    Font(R.font.dm_sans_bold, FontWeight.Bold)
)
private val BaseTypography = Typography()

private fun TextStyle.withDmSans(): TextStyle = copy(fontFamily = DmSansFontFamily)

val Typography = Typography(
    displayLarge = BaseTypography.displayLarge.withDmSans(),
    displayMedium = BaseTypography.displayMedium.withDmSans(),
    displaySmall = BaseTypography.displaySmall.withDmSans(),
    headlineLarge = BaseTypography.headlineLarge.withDmSans(),
    headlineMedium = BaseTypography.headlineMedium.withDmSans(),
    headlineSmall = BaseTypography.headlineSmall.withDmSans(),
    titleLarge = BaseTypography.titleLarge.withDmSans(),
    titleMedium = BaseTypography.titleMedium.withDmSans(),
    titleSmall = BaseTypography.titleSmall.withDmSans(),
    bodyLarge = BaseTypography.bodyLarge.withDmSans(),
    bodyMedium = BaseTypography.bodyMedium.withDmSans(),
    bodySmall = BaseTypography.bodySmall.withDmSans(),
    labelLarge = BaseTypography.labelLarge.withDmSans(),
    labelMedium = BaseTypography.labelMedium.withDmSans(),
    labelSmall = BaseTypography.labelSmall.withDmSans()
)
