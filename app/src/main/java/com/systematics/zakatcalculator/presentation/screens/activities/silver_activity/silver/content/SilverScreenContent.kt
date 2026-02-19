package com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.silver.content

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.systematics.zakatcalculator.R
import com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.silver.events.SilverEvent
import com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.silver.state.SilverCalculationResult
import com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.silver.state.SilverState
import com.systematics.zakatcalculator.presentation.screens.components.CommonAppBar
import com.systematics.zakatcalculator.presentation.screens.components.CommonInfoBox
import com.systematics.zakatcalculator.presentation.screens.components.CommonInfoExpandableItem
import com.systematics.zakatcalculator.presentation.screens.components.CommonInputFieldLabel
import com.systematics.zakatcalculator.presentation.screens.components.CommonPaidStatusCard
import com.systematics.zakatcalculator.presentation.screens.components.CommonZakatTabs
import com.systematics.zakatcalculator.presentation.screens.models.ZakatTab
import com.systematics.zakatcalculator.ui.theme.ZakatCalculatorTheme
import com.systematics.zakatcalculator.utils.Utils

@Composable
fun SilverScreenContent(
    state: SilverState,
    onEvent: (SilverEvent) -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CommonAppBar(
                title = stringResource(R.string.cat_silver),
                onBackClick = { (context as? Activity)?.finish() }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {
            // Header Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Column(Modifier.padding(16.dp)) {
                    CommonInfoBox(text = stringResource(R.string.silver_zakat_description))

                    Spacer(modifier = Modifier.height(24.dp))

                    CommonPaidStatusCard(
                        isPaid = state.isPaid,
                        isQualified = state.requirement1 && state.requirement2 && state.requirement3,
                        onTogglePaidStatus = { onEvent(SilverEvent.TogglePaidStatus) }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Requirements Section
                    var isRequirementsExpanded by remember { mutableStateOf(true) }
                    val allRequirementsMet =
                        state.requirement1 && state.requirement2 && state.requirement3
                    val requirementsCardShape = RoundedCornerShape(16.dp)
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0f)
                        ),
                        shape = requirementsCardShape,
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = if (allRequirementsMet) {
                                            listOf(
                                                MaterialTheme.colorScheme.primary,
                                                MaterialTheme.colorScheme.primaryContainer
                                            )
                                        } else {
                                            listOf(
                                                MaterialTheme.colorScheme.surface,
                                                MaterialTheme.colorScheme.surface
                                            )
                                        }
                                    ),
                                    shape = requirementsCardShape
                                )
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { isRequirementsExpanded = !isRequirementsExpanded },
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val requirementCount =
                                    (if (state.requirement1) 1 else 0) + (if (state.requirement2) 1 else 0) + (if (state.requirement3) 1 else 0)
                                Text(
                                    text = stringResource(
                                        R.string.requirements,
                                        requirementCount,
                                        3
                                    ),
                                    color = if (allRequirementsMet) {
                                        MaterialTheme.colorScheme.onSecondary
                                    } else {
                                        MaterialTheme.colorScheme.primary
                                    },
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Icon(
                                    if (isRequirementsExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }

                            AnimatedVisibility(visible = isRequirementsExpanded) {
                                Column {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    SilverRequirementItem(
                                        text = stringResource(R.string.silver_requirement_1),
                                        checked = state.requirement1,
                                        useOnSecondary = allRequirementsMet,
                                        onCheckedChange = {
                                            onEvent(
                                                SilverEvent.UpdateRequirement1(
                                                    it
                                                )
                                            )
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    SilverRequirementItem(
                                        text = stringResource(R.string.silver_requirement_2),
                                        checked = state.requirement2,
                                        useOnSecondary = allRequirementsMet,
                                        onCheckedChange = {
                                            onEvent(
                                                SilverEvent.UpdateRequirement2(
                                                    it
                                                )
                                            )
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    SilverRequirementItem(
                                        text = stringResource(R.string.silver_requirement_3),
                                        checked = state.requirement3,
                                        useOnSecondary = allRequirementsMet,
                                        onCheckedChange = {
                                            onEvent(
                                                SilverEvent.UpdateRequirement3(
                                                    it
                                                )
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Tabs
            CommonZakatTabs(
                modifier = Modifier.padding(horizontal = 16.dp),
                selectedTab = state.selectedTab,
                onTabChanged = { onEvent(SilverEvent.UpdateTab(it)) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Content Area
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                if (state.selectedTab == ZakatTab.Calculator) {
                    SilverCalculatorTabContent(state, onEvent)
                } else {
                    SilverZakatInfoTabContent()
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SilverRequirementItem(
    text: String,
    checked: Boolean,
    useOnSecondary: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            modifier = Modifier.weight(1f),
            fontSize = 14.sp,
            color = if (useOnSecondary) {
                MaterialTheme.colorScheme.onSecondary
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
        )
    }
}

@Composable
fun SilverCalculatorTabContent(state: SilverState, onEvent: (SilverEvent) -> Unit) {
    val context = LocalContext.current

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            if (state.calculationResult == null) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.calculate_zakat_silver),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
            }

            Text(
                text = stringResource(R.string.silver_calculation_prompt),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stringResource(R.string.nisab),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 16.dp)
                )
                Box(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.primaryContainer,
                            RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.silver_nisab_value),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
            }

            CommonInputFieldLabel(stringResource(R.string.silver_quantity))
            OutlinedTextField(
                value = state.silverQuantity,
                onValueChange = { onEvent(SilverEvent.UpdateSilverQuantity(it)) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(stringResource(R.string.silver_quantity_placeholder)) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    stringResource(R.string.silver_price),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    stringResource(R.string.find_current_price),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        Utils.searchOnWeb(context, context.getString(R.string.silver_price_per_gram))
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.silverPrice,
                onValueChange = { onEvent(SilverEvent.UpdateSilverPrice(it)) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(stringResource(R.string.silver_price_placeholder)) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onEvent(SilverEvent.Calculate) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    stringResource(R.string.calculate),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            }

            // Result Area
            state.calculationResult?.let { result ->
                Spacer(modifier = Modifier.height(24.dp))

                when (result) {
                    is SilverCalculationResult.Success -> {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(24.dp)) {
                                Text(
                                    stringResource(R.string.calculation_result),
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    stringResource(R.string.silver_zakat_options),
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    "${result.grams} ${stringResource(R.string.grams_of_silver)}",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 18.sp
                                )
                                Text(
                                    stringResource(R.string.or),
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                                Text(
                                    "${result.cash} ${stringResource(R.string.cash)}",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 18.sp
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Button(
                                        onClick = { onEvent(SilverEvent.ToggleSummary) },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(48.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                        shape = RoundedCornerShape(24.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Description,
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(stringResource(R.string.summary))
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    IconButton(
                                        onClick = { onEvent(SilverEvent.ResetCalculation) },
                                        modifier = Modifier
                                            .size(48.dp)
                                            .background(
                                                MaterialTheme.colorScheme.surface,
                                                CircleShape
                                            )
                                    ) {
                                        Icon(
                                            Icons.Default.Refresh,
                                            contentDescription = stringResource(R.string.reset)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    is SilverCalculationResult.BelowNisab -> {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(24.dp)) {
                                Text(
                                    stringResource(R.string.calculation_result),
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = stringResource(R.string.silver_below_nisab_message),
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Spacer(modifier = Modifier.height(24.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Button(
                                        onClick = { onEvent(SilverEvent.ToggleSummary) },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(48.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                        shape = RoundedCornerShape(24.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Description,
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(stringResource(R.string.summary))
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    IconButton(
                                        onClick = { onEvent(SilverEvent.ResetCalculation) },
                                        modifier = Modifier
                                            .size(48.dp)
                                            .background(
                                                MaterialTheme.colorScheme.surface,
                                                CircleShape
                                            )
                                    ) {
                                        Icon(
                                            Icons.Default.Refresh,
                                            contentDescription = stringResource(R.string.reset)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                if (state.showSummary) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = stringResource(R.string.calculation_summary),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            SummaryRow(stringResource(R.string.silver_quantity), state.silverQuantity)
                            SummaryRow(stringResource(R.string.silver_price), state.silverPrice)
                            SummaryRow(stringResource(R.string.nisab), stringResource(R.string.silver_nisab_value))
                            SummaryRow(
                                stringResource(R.string.zakat_calculation),
                                "${state.silverQuantity.ifBlank { "0" }} x 2.5%"
                            )
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                            val resultText = when (val calc = state.calculationResult) {
                                is SilverCalculationResult.Success -> "${calc.cash} ${stringResource(R.string.cash)}"
                                is SilverCalculationResult.BelowNisab -> stringResource(R.string.not_required)
                                else -> ""
                            }
                            SummaryRow(
                                stringResource(R.string.total_result),
                                resultText,
                                isBold = true
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryRow(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = MaterialTheme.colorScheme.outline, fontSize = 14.sp)
        Text(
            text = value,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp
        )
    }
}

@Composable
fun SilverZakatInfoTabContent() {
    var expandedItem by remember { mutableStateOf<Int?>(null) }

    Column {
        Text(
            text = stringResource(R.string.about_zakat_silver),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.silver_zakat_info_description),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(24.dp))

        CommonInfoExpandableItem(
            title = stringResource(R.string.do_i_have_to_pay),
            content = stringResource(R.string.silver_do_i_have_to_pay_content),
            isExpanded = expandedItem == 0,
            onToggle = { expandedItem = if (expandedItem == 0) null else 0 }
        )

        Spacer(modifier = Modifier.height(12.dp))

        CommonInfoExpandableItem(
            title = stringResource(R.string.how_to_pay),
            content = stringResource(R.string.silver_how_to_pay_content),
            isExpanded = expandedItem == 1,
            onToggle = { expandedItem = if (expandedItem == 1) null else 1 }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SilverScreenContentPreview() {
    ZakatCalculatorTheme {
        SilverScreenContent(
            state = SilverState(),
            onEvent = {}
        )
    }
}
