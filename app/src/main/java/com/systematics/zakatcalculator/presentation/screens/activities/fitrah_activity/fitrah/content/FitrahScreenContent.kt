package com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.content

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.systematics.zakatcalculator.R
import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.events.FitrahEvent
import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.state.FitrahState
import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.fitrah.state.FitrahTab
import com.systematics.zakatcalculator.ui.theme.ZakatCalculatorTheme

@Composable
fun FitrahScreenContent(
    state: FitrahState,
    onEvent: (FitrahEvent) -> Unit
) {
    val context = LocalContext.current

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {
            // Custom Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.primaryContainer
                            )
                        ),
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    )
                    .padding(top = 48.dp, bottom = 24.dp, start = 16.dp, end = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { (context as? Activity)?.finish() }) {
                        Icon(
                            Icons.Default.ChevronLeft,
                            contentDescription = stringResource(R.string.back),
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Text(
                        text = stringResource(R.string.cat_fitrah),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Info Box
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.fitrah_zakat_description),
                        modifier = Modifier.padding(16.dp),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Paid Status Row
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (state.isPaid) stringResource(R.string.paid) else stringResource(R.string.not_yet_paid),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )

                            Button(
                                onClick = { onEvent(FitrahEvent.TogglePaidStatus) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary,
                                    contentColor = MaterialTheme.colorScheme.onSecondary
                                )
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(stringResource(R.string.mark_as_done))
                            }
                        }
                    }
                }


                Spacer(modifier = Modifier.height(16.dp))

                // Requirements Section
                var isRequirementsExpanded by remember { mutableStateOf(true) }
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { isRequirementsExpanded = !isRequirementsExpanded },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.requirements, (if (state.canGiveRice) 1 else 0) + (if (state.hasExcessFood) 1 else 0)),
                                color = MaterialTheme.colorScheme.primary,
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

                                RequirementItem(
                                    text = stringResource(R.string.fitrah_requirement_1),
                                    checked = state.canGiveRice,
                                    onCheckedChange = { onEvent(FitrahEvent.UpdateCanGiveRice(it)) }
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                RequirementItem(
                                    text = stringResource(R.string.fitrah_requirement_2),
                                    checked = state.hasExcessFood,
                                    onCheckedChange = { onEvent(FitrahEvent.UpdateHasExcessFood(it)) }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(32.dp))
                        .padding(4.dp)
                ) {
                    TabItem(
                        text = stringResource(R.string.calculate),
                        isSelected = state.selectedTab == FitrahTab.Calculator,
                        modifier = Modifier.weight(1f),
                        onClick = { onEvent(FitrahEvent.UpdateTab(FitrahTab.Calculator)) }
                    )
                    TabItem(
                        text = stringResource(R.string.zakat_info),
                        isSelected = state.selectedTab == FitrahTab.ZakatInfo,
                        modifier = Modifier.weight(1f),
                        onClick = { onEvent(FitrahEvent.UpdateTab(FitrahTab.ZakatInfo)) }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Calculator Content
                if (state.selectedTab == FitrahTab.Calculator) {
                    CalculatorSection(state, onEvent)
                } else {
                    InfoSection()
                }
            }
        }
    }
}

@Composable
fun RequirementItem(text: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            modifier = Modifier.weight(1f),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun TabItem(text: String, isSelected: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(
        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
        shape = RoundedCornerShape(32.dp),
        modifier = modifier.clickable { onClick() }
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(vertical = 12.dp),
            textAlign = TextAlign.Center,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.outline,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun CalculatorSection(state: FitrahState, onEvent: (FitrahEvent) -> Unit) {
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
                Icon(
                    Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
            Text(
                text = stringResource(R.string.fitrah_calculation_prompt),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            InputFieldLabel(stringResource(R.string.number_of_people))
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
                    modifier = Modifier.weight(1f).padding(start = 16.dp),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                IconButton(
                    onClick = { onEvent(FitrahEvent.IncrementPeople) },
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary, CircleShape).size(32.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = { onEvent(FitrahEvent.DecrementPeople) },
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary, CircleShape).size(32.dp)
                ) {
                    Icon(Icons.Default.Remove, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(20.dp))
                }
            }

            InputFieldLabel(stringResource(R.string.pay_with))
            DropdownField(state.payWith, listOf(stringResource(R.string.rice), stringResource(R.string.money))) { onEvent(FitrahEvent.UpdatePayWith(it)) }

            InputFieldLabel(stringResource(R.string.unit_of_rice))
            DropdownField(state.unit, listOf("Kg", "Litre")) { onEvent(FitrahEvent.UpdateUnit(it)) }

            if (state.payWith == stringResource(R.string.money)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    InputFieldLabel(stringResource(R.string.price_of_rice), Modifier.padding(top = 0.dp))
                    Text(
                        text = stringResource(R.string.find_current_price),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { /* Link to external or internal price finder */ }
                    )
                }

                OutlinedTextField(
                    value = state.pricePerUnit,
                    onValueChange = { onEvent(FitrahEvent.UpdatePrice(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(32.dp),
                    trailingIcon = { Text("${stringResource(R.string.per)} ${state.unit}", modifier = Modifier.padding(end = 16.dp), color = MaterialTheme.colorScheme.outline) },
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
                Text(stringResource(R.string.calculate), color = MaterialTheme.colorScheme.onPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            state.result?.let {
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
                            text = state.result,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = if (state.payWith == stringResource(R.string.money)) stringResource(R.string.total_amount_in_money) else stringResource(R.string.kg_of_rice_or_staple_food),
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
                                    Icon(Icons.Default.Description, contentDescription = null, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(stringResource(R.string.summary), fontWeight = FontWeight.Bold)
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
                            SummaryRow(stringResource(R.string.number_of_people), "${state.numberOfPeople}")
                            SummaryRow(stringResource(R.string.payment_method), state.payWith)
                            if (state.payWith == "Money") {
                                SummaryRow("${stringResource(R.string.price_per)} ${state.unit}", state.pricePerUnit)
                            } else {
                                SummaryRow(stringResource(R.string.unit), state.unit)
                            }
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                            SummaryRow(stringResource(R.string.total_result), state.result, isBold = true)
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
fun InputFieldLabel(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(top = 16.dp, bottom = 8.dp)
    )
}

@Composable
fun DropdownField(value: String, options: List<String>, onValueChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(32.dp))
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(32.dp))
            .clickable { expanded = true }
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = value, modifier = Modifier.weight(1f), fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = { 
                        onValueChange(option)
                        expanded = false 
                    }
                )
            }
        }
    }
}

@Composable
fun InfoSection() {
    var expandedItem by remember { mutableStateOf<Int?>(null) }
    Column {
        InfoExpandableItem(
            title = stringResource(R.string.do_i_have_to_pay),
            content = stringResource(R.string.fitrah_do_i_have_to_pay_content),
            isExpanded = expandedItem == 0,
            onToggle = { expandedItem = if (expandedItem == 0) null else 0 }
        )

        Spacer(modifier = Modifier.height(12.dp))

        InfoExpandableItem(
            title = stringResource(R.string.how_to_pay),
            content = stringResource(R.string.fitrah_how_to_pay_content),
            isExpanded = expandedItem == 1,
            onToggle = { expandedItem = if (expandedItem == 1) null else 1 }
        )
    }
}

@Composable
fun InfoExpandableItem(title: String, content: String, isExpanded: Boolean, onToggle: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = if (isExpanded) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        border = if (!isExpanded) BorderStroke(1.dp, MaterialTheme.colorScheme.onSurfaceVariant) else null,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Icon(
                    if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Text(
                    text = content,
                    modifier = Modifier.padding(top = 16.dp),
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
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
                selectedTab = FitrahTab.Calculator,
                numberOfPeople = 2,
                payWith = "Rice",
                unit = "Kg",
                pricePerUnit = "250",
                result = "5.0 Kg"
            ),
            onEvent = {}
        )
    }
}
