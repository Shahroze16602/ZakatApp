package com.systematics.zakatcalculator.presentation.screens.activities.rikaz_activity.rikaz.content

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import com.systematics.zakatcalculator.presentation.screens.activities.rikaz_activity.rikaz.events.RikazEvent
import com.systematics.zakatcalculator.presentation.screens.activities.rikaz_activity.rikaz.state.RikazState
import com.systematics.zakatcalculator.presentation.screens.components.CommonAppBar
import com.systematics.zakatcalculator.presentation.screens.components.CommonInfoBox
import com.systematics.zakatcalculator.presentation.screens.components.CommonInfoExpandableItem
import com.systematics.zakatcalculator.presentation.screens.components.CommonPaidStatusCard
import com.systematics.zakatcalculator.presentation.screens.components.CommonZakatTabs
import com.systematics.zakatcalculator.presentation.screens.models.ZakatTab

@Composable
fun RikazScreenContent(
    state: RikazState,
    onEvent: (RikazEvent) -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CommonAppBar(
                title = stringResource(R.string.cat_rikaz),
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
                CommonInfoBox(text = stringResource(R.string.rikaz_zakat_description))

                Spacer(modifier = Modifier.height(16.dp))

                CommonPaidStatusCard(
                    isPaid = state.isPaid,
                    isQualified = state.requirement1,
                    onTogglePaidStatus = { onEvent(RikazEvent.TogglePaidStatus) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                var isRequirementsExpanded by remember { mutableStateOf(true) }
                RikazRequirementsSection(
                    requirement1 = state.requirement1,
                    isRequirementsExpanded = isRequirementsExpanded,
                    onRequirementsExpandedChange = { isRequirementsExpanded = it },
                    onRequirement1Changed = { onEvent(RikazEvent.UpdateRequirement1(it)) }
                )

                Spacer(modifier = Modifier.height(24.dp))

                CommonZakatTabs(
                    selectedTab = state.selectedTab,
                    onTabChanged = { onEvent(RikazEvent.UpdateTab(it)) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (state.selectedTab == ZakatTab.Calculator) {
                    RikazCalculatorSection(state = state, onEvent = onEvent)
                } else {
                    RikazInfoSection()
                }
            }
        }
    }
}

@Composable
private fun RikazRequirementsSection(
    requirement1: Boolean,
    isRequirementsExpanded: Boolean,
    onRequirementsExpandedChange: (Boolean) -> Unit,
    onRequirement1Changed: (Boolean) -> Unit
) {
    val allRequirementsMet = requirement1
    val requirementsCardShape = RoundedCornerShape(16.dp)

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0f)),
        shape = requirementsCardShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
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
                    .clickable { onRequirementsExpandedChange(!isRequirementsExpanded) },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.requirements, if (requirement1) 1 else 0, 1),
                    color = if (allRequirementsMet) {
                        MaterialTheme.colorScheme.onSecondary
                    } else {
                        MaterialTheme.colorScheme.primary
                    },
                    fontWeight = FontWeight.Bold
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
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onRequirement1Changed(!requirement1) },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.rikaz_requirement_1),
                            modifier = Modifier.weight(1f),
                            color = if (allRequirementsMet) {
                                MaterialTheme.colorScheme.onSecondary
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        )
                        Checkbox(
                            checked = requirement1,
                            onCheckedChange = onRequirement1Changed,
                            colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                        )
                    }
                }
            }
        }
        }
    }
}

@Composable
private fun RikazCalculatorSection(state: RikazState, onEvent: (RikazEvent) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (state.zakatAmount == null) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.calculate_zakat_rikaz),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.rikaz_calculation_prompt),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.treasureValue,
                    onValueChange = { onEvent(RikazEvent.UpdateTreasureValue(it)) },
                    label = { Text(stringResource(R.string.value_of_treasure)) },
                    placeholder = { Text(stringResource(R.string.amount)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { onEvent(RikazEvent.CalculateZakat) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = stringResource(R.string.calculate))
                }
            }
            }
        }

        state.zakatAmount?.let { amount ->
            Spacer(modifier = Modifier.height(24.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                ),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = stringResource(R.string.calculation_result),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = amount,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = stringResource(R.string.cash),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = { onEvent(RikazEvent.ToggleSummary) },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Icon(Icons.Default.Description, contentDescription = null)
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(stringResource(R.string.summary))
                        }

                        IconButton(
                            onClick = { onEvent(RikazEvent.ResetCalculation) },
                            modifier = Modifier
                                .size(48.dp)
                                .background(MaterialTheme.colorScheme.surface, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
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

                        SummaryRow(stringResource(R.string.value_of_treasure), state.treasureValue)
                        SummaryRow(
                            stringResource(R.string.zakat_calculation),
                            "${state.treasureValue.ifBlank { "0" }} x 20%"
                        )
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        SummaryRow(
                            stringResource(R.string.total_result),
                            amount,
                            isBold = true
                        )
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
private fun RikazInfoSection() {
    var isDoIHaveToPayExpanded by remember { mutableStateOf(false) }
    var isHowToPayExpanded by remember { mutableStateOf(false) }

    Column {
        CommonInfoExpandableItem(
            title = stringResource(R.string.do_i_have_to_pay),
            content = stringResource(R.string.rikaz_do_i_have_to_pay_content),
            isExpanded = isDoIHaveToPayExpanded,
            onToggle = { isDoIHaveToPayExpanded = !isDoIHaveToPayExpanded }
        )

        Spacer(modifier = Modifier.height(12.dp))

        CommonInfoExpandableItem(
            title = stringResource(R.string.how_to_pay),
            content = stringResource(R.string.rikaz_how_to_pay_content),
            isExpanded = isHowToPayExpanded,
            onToggle = { isHowToPayExpanded = !isHowToPayExpanded }
        )
    }
}
