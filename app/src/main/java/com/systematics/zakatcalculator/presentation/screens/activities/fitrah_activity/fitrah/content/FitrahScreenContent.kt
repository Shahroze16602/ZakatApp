package com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.content

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.systematics.zakatcalculator.R
import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.events.FitrahEvent
import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.state.FitrahPayWith
import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.state.FitrahResult
import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.state.FitrahState
import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.state.FitrahUnit
import com.systematics.zakatcalculator.presentation.screens.components.CommonAppBar
import com.systematics.zakatcalculator.presentation.screens.components.CommonDropdownField
import com.systematics.zakatcalculator.presentation.screens.components.CommonInfoBox
import com.systematics.zakatcalculator.presentation.screens.components.CommonInfoExpandableItem
import com.systematics.zakatcalculator.presentation.screens.components.CommonInputFieldLabel
import com.systematics.zakatcalculator.presentation.screens.components.CommonPaidStatusCard
import com.systematics.zakatcalculator.presentation.screens.components.CommonRequirementsCard
import com.systematics.zakatcalculator.presentation.screens.components.CommonZakatTabs
import com.systematics.zakatcalculator.presentation.screens.models.ZakatTab
import com.systematics.zakatcalculator.ui.theme.ZakatCalculatorTheme
import com.systematics.zakatcalculator.utils.Utils

@Composable
fun FitrahScreenContent(
    state: FitrahState,
    onEvent: (FitrahEvent) -> Unit
) {
    val context = LocalContext.current

    Scaffold(topBar = {
        CommonAppBar(
            title = stringResource(R.string.cat_fitrah),
            onBackClick = { (context as? Activity)?.finish() }
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                CommonInfoBox(
                    text = stringResource(R.string.fitrah_zakat_description)
                )

                Spacer(modifier = Modifier.height(16.dp))

                CommonPaidStatusCard(
                    isPaid = state.isPaid,
                    onTogglePaidStatus = { onEvent(FitrahEvent.TogglePaidStatus) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                var isRequirementsExpanded by remember { mutableStateOf(true) }
                CommonRequirementsCard(
                    canGiveRice = state.canGiveRice,
                    hasExcessFood = state.hasExcessFood,
                    isRequirementsExpanded = isRequirementsExpanded,
                    onRequirementsExpandedChange = { isRequirementsExpanded = it },
                    updateCanGiveRice = { onEvent(FitrahEvent.UpdateCanGiveRice(it)) },
                    updateHasExcessFood = { onEvent(FitrahEvent.UpdateHasExcessFood(it)) },
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Tabs
                CommonZakatTabs(
                    selectedTab = state.selectedTab,
                    onTabChanged = {
                        onEvent(FitrahEvent.UpdateTab(it))
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Calculator Content
                if (state.selectedTab == ZakatTab.Calculator) {
                    CalculatorSection(context, state, onEvent)
                } else {
                    InfoSection()
                }
            }
        }
    }
}

@Composable
fun CalculatorSection(context: Context, state: FitrahState, onEvent: (FitrahEvent) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.calculate_zakat_fitrah),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
            }
            Text(
                text = stringResource(R.string.fitrah_calculation_prompt),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            CommonInputFieldLabel(stringResource(R.string.number_of_people))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(32.dp))
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(32.dp))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = state.numberOfPeople.toString().padStart(2, '0'),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                IconButton(
                    onClick = { onEvent(FitrahEvent.IncrementPeople) },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                        .size(32.dp)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = { onEvent(FitrahEvent.DecrementPeople) },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                        .size(32.dp)
                ) {
                    Icon(
                        Icons.Default.Remove,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            val payWithOptions = FitrahPayWith.entries
            CommonInputFieldLabel(stringResource(R.string.pay_with))
            CommonDropdownField(
                value = stringResource(state.payWith.labelRes),
                options = payWithOptions.map { stringResource(it.labelRes) }
            ) { selectedLabel ->
                val selected = payWithOptions.first { context.getString(it.labelRes) == selectedLabel }
                onEvent(FitrahEvent.UpdatePayWith(selected))
            }

            val unitOptions = FitrahUnit.entries
            CommonInputFieldLabel(stringResource(R.string.unit_of_rice))
            CommonDropdownField(
                value = stringResource(state.unit.labelRes),
                options = unitOptions.map { stringResource(it.labelRes) }
            ) { selectedLabel ->
                val selected = unitOptions.first { context.getString(it.labelRes) == selectedLabel }
                onEvent(FitrahEvent.UpdateUnit(selected))
            }

            if (state.payWith == FitrahPayWith.MONEY) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CommonInputFieldLabel(
                        stringResource(R.string.price_of_rice),
                        Modifier.padding(top = 0.dp)
                    )
                    Text(
                        text = stringResource(R.string.find_current_price),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            Utils.searchOnWeb(
                                context = context,
                                query = context.getString(
                                    if (state.unit == FitrahUnit.KG) {
                                        R.string.rice_price_per_kg
                                    } else {
                                        R.string.rice_price_per_litre
                                    }
                                )
                            )
                        }
                    )
                }

                OutlinedTextField(
                    value = state.pricePerUnit,
                    onValueChange = { onEvent(FitrahEvent.UpdatePrice(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(32.dp),
                    trailingIcon = {
                        Text(
                            "${stringResource(R.string.per)} ${stringResource(state.unit.labelRes)}",
                            modifier = Modifier.padding(end = 16.dp),
                            color = MaterialTheme.colorScheme.outline
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onEvent(FitrahEvent.Calculate) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(32.dp)
            ) {
                Text(
                    stringResource(R.string.calculate),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            state.result?.let { result ->
                Spacer(modifier = Modifier.height(24.dp))

                // Calculation Result Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(
                            text = stringResource(R.string.calculation_result),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(R.string.fitrah_zakat_result_prompt),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = when (result) {
                                is FitrahResult.Money -> result.amount
                                is FitrahResult.Rice -> "${result.amount} ${stringResource(result.unit.labelRes)}"
                            },
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = if (state.payWith == FitrahPayWith.MONEY) stringResource(
                                R.string.total_amount_in_money
                            ) else stringResource(R.string.kg_of_rice_or_staple_food),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = { onEvent(FitrahEvent.ToggleSummary) },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(56.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                shape = RoundedCornerShape(28.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Default.Description,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        stringResource(R.string.summary),
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            IconButton(
                                onClick = { onEvent(FitrahEvent.Reset) },
                                modifier = Modifier
                                    .size(56.dp)
                                    .background(MaterialTheme.colorScheme.surface, CircleShape)
                            ) {
                                Icon(
                                    Icons.Default.Refresh,
                                    contentDescription = stringResource(R.string.reset),
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.size(28.dp)
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
                            SummaryRow(
                                stringResource(R.string.number_of_people),
                                "${state.numberOfPeople}"
                            )
                            SummaryRow(
                                stringResource(R.string.payment_method),
                                stringResource(state.payWith.labelRes)
                            )
                            if (state.payWith == FitrahPayWith.MONEY) {
                                SummaryRow(
                                    "${stringResource(R.string.price_per)} ${stringResource(state.unit.labelRes)}",
                                    state.pricePerUnit
                                )
                            } else {
                                SummaryRow(stringResource(R.string.unit), stringResource(state.unit.labelRes))
                            }
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                            SummaryRow(
                                stringResource(R.string.total_result),
                                when (result) {
                                    is FitrahResult.Money -> result.amount
                                    is FitrahResult.Rice -> "${result.amount} ${stringResource(result.unit.labelRes)}"
                                },
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
fun SummaryRow(label: String, value: String, isBold: Boolean = false) {
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
fun InfoSection() {
    var expandedItem by remember { mutableStateOf<Int?>(null) }
    Column {
        CommonInfoExpandableItem(
            title = stringResource(R.string.do_i_have_to_pay),
            content = stringResource(R.string.fitrah_do_i_have_to_pay_content),
            isExpanded = expandedItem == 0,
            onToggle = { expandedItem = if (expandedItem == 0) null else 0 }
        )

        Spacer(modifier = Modifier.height(12.dp))

        CommonInfoExpandableItem(
            title = stringResource(R.string.how_to_pay),
            content = stringResource(R.string.fitrah_how_to_pay_content),
            isExpanded = expandedItem == 1,
            onToggle = { expandedItem = if (expandedItem == 1) null else 1 }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FitrahScreenContentPreview() {
    ZakatCalculatorTheme {
        FitrahScreenContent(
            state = FitrahState(
                isPaid = false,
                canGiveRice = true,
                hasExcessFood = true,
                selectedTab = ZakatTab.Calculator,
                numberOfPeople = 2,
                payWith = FitrahPayWith.RICE,
                unit = FitrahUnit.KG,
                pricePerUnit = "",
                result = null
            ),
            onEvent = {}
        )
    }
}
