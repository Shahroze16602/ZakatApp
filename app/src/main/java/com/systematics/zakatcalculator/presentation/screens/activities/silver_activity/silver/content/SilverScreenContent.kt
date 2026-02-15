package com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.silver.content

import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.silver.events.SilverEvent
import com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.silver.state.SilverCalculationResult
import com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.silver.state.SilverState
import com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.silver.state.SilverTab
import com.systematics.zakatcalculator.ui.theme.ZakatCalculatorTheme

@Composable
fun SilverScreenContent(
    state: SilverState,
    onEvent: (SilverEvent) -> Unit
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
            // Header Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
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
                            text = stringResource(R.string.cat_silver),
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(Modifier.padding(16.dp)){
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

                    Spacer(modifier = Modifier.height(24.dp))

                    // Status Card
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(32.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (state.isPaid) stringResource(R.string.paid) else stringResource(
                                        R.string.not_yet_paid
                                    ),
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        stringResource(R.string.once_per_year),
                                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(
                                            alpha = 0.8f
                                        ),
                                        fontSize = 12.sp
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(
                                        Icons.Default.Info,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    onClick = { onEvent(SilverEvent.TogglePaidStatus) },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                                    shape = RoundedCornerShape(24.dp),
                                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                                ) {
                                    Icon(
                                        Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
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
                                val requirementCount =
                                    (if (state.requirement1) 1 else 0) + (if (state.requirement2) 1 else 0) + (if (state.requirement3) 1 else 0)
                                Text(
                                    text = stringResource(R.string.requirements, requirementCount),
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
                                    SilverRequirementItem(
                                        text = stringResource(R.string.silver_requirement_1),
                                        checked = state.requirement1,
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(32.dp))
                    .padding(4.dp)
            ) {
                TabItem(
                    text = stringResource(R.string.calculate),
                    isSelected = state.selectedTab == SilverTab.Calculator,
                    modifier = Modifier.weight(1f),
                    onClick = { onEvent(SilverEvent.UpdateTab(SilverTab.Calculator)) }
                )
                TabItem(
                    text = stringResource(R.string.zakat_info),
                    isSelected = state.selectedTab == SilverTab.ZakatInfo,
                    modifier = Modifier.weight(1f),
                    onClick = { onEvent(SilverEvent.UpdateTab(SilverTab.ZakatInfo)) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Content Area
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                if (state.selectedTab == SilverTab.Calculator) {
                    SilverCalculatorTabContent(state, onEvent)
                } else {
                    SilverZakatInfoTabContent()
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SilverRequirementItem(text: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
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
fun SilverCalculatorTabContent(state: SilverState, onEvent: (SilverEvent) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.calculate_zakat_silver),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                Icon(Icons.Default.HelpOutline, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
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
                Text(stringResource(R.string.nisab), fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 16.dp))
                Box(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.primaryContainer,
                            RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(text = stringResource(R.string.silver_nisab_value), color = MaterialTheme.colorScheme.onPrimaryContainer, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Icon(Icons.Default.HelpOutline, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
            }

            SilverInputFieldLabel(stringResource(R.string.silver_quantity))
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
                Text(stringResource(R.string.silver_price), fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(4.dp))
                Text(stringResource(R.string.find_current_price), color = MaterialTheme.colorScheme.primary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(stringResource(R.string.set_silver_price_prompt), fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(stringResource(R.string.set_price))
                }
            }

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
                Text(stringResource(R.string.calculate), fontWeight = FontWeight.Bold, fontSize = 16.sp)
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
                                Text(stringResource(R.string.calculation_result), fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(stringResource(R.string.silver_zakat_options), fontSize = 14.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                                Spacer(modifier = Modifier.height(16.dp))
                                Text("${result.grams} ${stringResource(R.string.grams_of_silver)}", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                                Text(stringResource(R.string.or), fontSize = 14.sp, color = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.padding(vertical = 4.dp))
                                Text("${result.cash} ${stringResource(R.string.cash)}", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)

                                Spacer(modifier = Modifier.height(24.dp))

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Button(
                                        onClick = { },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(48.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                        shape = RoundedCornerShape(24.dp)
                                    ) {
                                        Icon(Icons.Default.Description, contentDescription = null, modifier = Modifier.size(18.dp))
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
                                        Icon(Icons.Default.Refresh, contentDescription = stringResource(R.string.reset))
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
                                Text(stringResource(R.string.calculation_result), fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = stringResource(R.string.silver_below_nisab_message),
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Spacer(modifier = Modifier.height(24.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Button(
                                        onClick = { },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(48.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                        shape = RoundedCornerShape(24.dp)
                                    ) {
                                        Icon(Icons.Default.Description, contentDescription = null, modifier = Modifier.size(18.dp))
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
                                        Icon(Icons.Default.Refresh, contentDescription = stringResource(R.string.reset))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
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

        SilverInfoExpandableItem(
            title = stringResource(R.string.do_i_have_to_pay),
            content = stringResource(R.string.silver_do_i_have_to_pay_content),
            isExpanded = expandedItem == 0,
            onToggle = { expandedItem = if (expandedItem == 0) null else 0 }
        )

        Spacer(modifier = Modifier.height(12.dp))

        SilverInfoExpandableItem(
            title = stringResource(R.string.how_to_pay),
            content = stringResource(R.string.silver_how_to_pay_content),
            isExpanded = expandedItem == 1,
            onToggle = { expandedItem = if (expandedItem == 1) null else 1 }
        )
    }
}

@Composable
fun SilverInfoExpandableItem(title: String, content: String, isExpanded: Boolean, onToggle: () -> Unit) {
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

@Composable
fun SilverInputFieldLabel(text: String) {
    Text(text = text, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
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
