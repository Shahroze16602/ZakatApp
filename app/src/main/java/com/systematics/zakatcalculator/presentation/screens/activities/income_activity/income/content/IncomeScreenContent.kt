package com.systematics.zakatcalculator.presentation.screens.activities.income_activity.income.content

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.systematics.zakatcalculator.R
import com.systematics.zakatcalculator.presentation.screens.activities.income_activity.income.events.IncomeEvent
import com.systematics.zakatcalculator.presentation.screens.activities.income_activity.income.state.IncomeCalculationResult
import com.systematics.zakatcalculator.presentation.screens.activities.income_activity.income.state.IncomeState
import com.systematics.zakatcalculator.presentation.screens.components.CommonAppBar
import com.systematics.zakatcalculator.presentation.screens.components.CommonInfoBox
import com.systematics.zakatcalculator.presentation.screens.components.CommonInfoExpandableItem
import com.systematics.zakatcalculator.presentation.screens.components.CommonPaidStatusCard
import com.systematics.zakatcalculator.presentation.screens.components.CommonZakatTabs
import com.systematics.zakatcalculator.presentation.screens.models.ZakatTab
import com.systematics.zakatcalculator.utils.Utils

@Composable
fun IncomeScreenContent(
    state: IncomeState,
    onEvent: (IncomeEvent) -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CommonAppBar(
                title = stringResource(R.string.cat_income),
                onBackClick = { (context as? Activity)?.finish() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.padding(16.dp).padding(bottom = 24.dp)) {
                CommonInfoBox(text = stringResource(R.string.income_zakat_description))

                Spacer(modifier = Modifier.height(16.dp))

                CommonPaidStatusCard(
                    isPaid = state.isPaid,
                    onTogglePaidStatus = { onEvent(IncomeEvent.TogglePaidStatus) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                RequirementsSection(state = state, onEvent = onEvent)

                Spacer(modifier = Modifier.height(24.dp))

                CommonZakatTabs(
                    selectedTab = state.selectedTab,
                    onTabChanged = { onEvent(IncomeEvent.UpdateTab(it)) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (state.selectedTab == ZakatTab.Calculator) {
                    IncomeCalculatorSection(state = state, onEvent = onEvent)
                } else {
                    IncomeInfoSection()
                }
            }
        }
    }
}

@Composable
private fun RequirementsSection(state: IncomeState, onEvent: (IncomeEvent) -> Unit) {
    var isRequirementsExpanded by remember { mutableStateOf(true) }
    val allRequirementsMet =
        state.requirement1 && state.requirement2 && state.requirement3 && state.requirement4
    val requirementsCardShape = RoundedCornerShape(16.dp)
    val requirementsBrush = Brush.horizontalGradient(
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
    )

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0f)),
        shape = requirementsCardShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Canvas(modifier = Modifier.matchParentSize()) {
                drawRoundRect(
                    brush = requirementsBrush,
                    cornerRadius = CornerRadius(
                        requirementsCardShape.topStart.toPx(size, this),
                        requirementsCardShape.topStart.toPx(size, this)
                    )
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isRequirementsExpanded = !isRequirementsExpanded },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(
                        R.string.requirements,
                        listOf(
                            state.requirement1,
                            state.requirement2,
                            state.requirement3,
                            state.requirement4
                        ).count { it },
                        4
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
                    imageVector = if (isRequirementsExpanded) {
                        Icons.Default.KeyboardArrowUp
                    } else {
                        Icons.Default.KeyboardArrowDown
                    },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            AnimatedVisibility(visible = isRequirementsExpanded) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))

                    RequirementItem(
                        text = stringResource(R.string.income_requirement_1),
                        checked = state.requirement1,
                        useOnSecondary = allRequirementsMet,
                        onCheckedChange = { onEvent(IncomeEvent.UpdateRequirement1(it)) }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    RequirementItem(
                        text = stringResource(R.string.income_requirement_2),
                        checked = state.requirement2,
                        useOnSecondary = allRequirementsMet,
                        onCheckedChange = { onEvent(IncomeEvent.UpdateRequirement2(it)) }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    RequirementItem(
                        text = stringResource(R.string.income_requirement_3),
                        checked = state.requirement3,
                        useOnSecondary = allRequirementsMet,
                        onCheckedChange = { onEvent(IncomeEvent.UpdateRequirement3(it)) }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    RequirementItem(
                        text = stringResource(R.string.income_requirement_4),
                        checked = state.requirement4,
                        useOnSecondary = allRequirementsMet,
                        onCheckedChange = { onEvent(IncomeEvent.UpdateRequirement4(it)) }
                    )
                }
            }
        }
        }
    }
}

@Composable
private fun RequirementItem(
    text: String,
    checked: Boolean,
    useOnSecondary: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(vertical = 8.dp),
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
private fun IncomeCalculatorSection(state: IncomeState, onEvent: (IncomeEvent) -> Unit) {
    val context = LocalContext.current

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.calculate_zakat_income),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.income_calculation_prompt),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.income,
                onValueChange = { onEvent(IncomeEvent.UpdateIncome(it)) },
                label = { Text(stringResource(R.string.income_label)) },
                placeholder = { Text(stringResource(R.string.income_placeholder)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.expense,
                onValueChange = { onEvent(IncomeEvent.UpdateExpense(it)) },
                label = { Text(stringResource(R.string.expense_label)) },
                placeholder = { Text(stringResource(R.string.expense_placeholder)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                stringResource(R.string.gold_price),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            OutlinedTextField(
                value = state.goldPrice,
                onValueChange = { onEvent(IncomeEvent.UpdateGoldPrice(it)) },
                label = { Text(stringResource(R.string.current_gold_price_per_gram)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            TextButton(onClick = { Utils.searchOnWeb(context, context.getString(R.string.gold_price_per_gram)) }) {
                Text(text = stringResource(R.string.find_current_price))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onEvent(IncomeEvent.Calculate) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = stringResource(R.string.calculate))
            }

            state.calculationResult?.let { result ->
                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(
                            text = stringResource(R.string.calculation_result),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        when (result) {
                            is IncomeCalculationResult.Success -> {
                                Text(
                                    text = result.amount,
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Text(
                                    text = stringResource(R.string.cash),
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }

                            IncomeCalculationResult.BelowNisab -> {
                                Text(
                                    text = stringResource(R.string.income_below_nisab_message),
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = { onEvent(IncomeEvent.ToggleSummary) },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Icon(Icons.Default.Description, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(stringResource(R.string.summary))
                            }

                            IconButton(
                                onClick = { onEvent(IncomeEvent.ResetCalculation) },
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(MaterialTheme.colorScheme.surface, CircleShape)
                            ) {
                                Icon(
                                    Icons.Default.Refresh,
                                    contentDescription = stringResource(R.string.reset)
                                )
                            }
                        }
                    }
                }

                if (state.showSummary) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(16.dp),
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

                            SummaryRow(stringResource(R.string.income_label), state.income)
                            SummaryRow(stringResource(R.string.expense_label), state.expense)
                            SummaryRow(stringResource(R.string.gold_price), state.goldPrice)

                            val netIncome = ((state.income.toDoubleOrNull() ?: 0.0) - (state.expense.toDoubleOrNull()
                                ?: 0.0)).coerceAtLeast(0.0)
                            val nisab = (state.goldPrice.toDoubleOrNull() ?: 0.0) * 85.0

                            SummaryRow(
                                stringResource(R.string.net_income),
                                String.format("%,.0f", netIncome)
                            )
                            SummaryRow(
                                stringResource(R.string.nisab_threshold),
                                String.format("%,.0f", nisab)
                            )

                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                            SummaryRow(
                                stringResource(R.string.total_result),
                                if (result is IncomeCalculationResult.Success) result.amount else stringResource(
                                    R.string.not_required
                                ),
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
private fun IncomeInfoSection() {
    var expandedItem by remember { mutableStateOf<Int?>(null) }

    Column {
        CommonInfoExpandableItem(
            title = stringResource(R.string.do_i_have_to_pay),
            content = stringResource(R.string.income_do_i_have_to_pay_content),
            isExpanded = expandedItem == 0,
            onToggle = { expandedItem = if (expandedItem == 0) null else 0 }
        )

        Spacer(modifier = Modifier.height(12.dp))

        CommonInfoExpandableItem(
            title = stringResource(R.string.how_to_pay),
            content = stringResource(R.string.income_how_to_pay_content),
            isExpanded = expandedItem == 1,
            onToggle = { expandedItem = if (expandedItem == 1) null else 1 }
        )
    }
}
