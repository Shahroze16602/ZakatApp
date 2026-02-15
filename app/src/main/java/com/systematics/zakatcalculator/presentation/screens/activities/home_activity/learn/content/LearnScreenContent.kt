package com.systematics.zakatcalculator.presentation.screens.activities.home_activity.learn.content

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.PanTool
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.systematics.zakatcalculator.R
import com.systematics.zakatcalculator.presentation.screens.components.CommonAppBar
import com.systematics.zakatcalculator.ui.theme.ZakatCalculatorTheme

@Composable
fun LearnScreenContent(onBackClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {
        CommonAppBar(
            title = stringResource(R.string.learn),
            onBackClick = onBackClick
        )
        Spacer(modifier = Modifier.height(24.dp))
        LearnList()
    }
}

@Composable
fun LearnList() {
    val items = listOf(
        LearnItemData(
            title = stringResource(R.string.definition_purpose),
            icon = Icons.AutoMirrored.Filled.MenuBook,
            content = { DefinitionPurposeContent() }
        ),
        LearnItemData(
            title = stringResource(R.string.who_to_give),
            icon = Icons.Default.Groups,
            content = { WhoToGiveContent() }
        ),
        LearnItemData(
            title = stringResource(R.string.types_of_zakat),
            icon = Icons.Default.Book,
            content = { TypesOfZakatContent() }
        ),
        LearnItemData(
            title = stringResource(R.string.dua_giving_zakat),
            icon = Icons.Default.PanTool,
            content = { DuaContent() }
        )
    )

    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items.forEach { item ->
            ExpandableLearnItemCard(item = item)
        }
    }
}

data class LearnItemData(val title: String, val icon: ImageVector, val content: @Composable () -> Unit)

@Composable
fun ExpandableLearnItemCard(item: LearnItemData) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { expanded = !expanded },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.title,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(MaterialTheme.colorScheme.onPrimaryContainer, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            AnimatedVisibility(visible = expanded) {
                item.content()
            }
        }
    }
}

@Composable
fun DefinitionPurposeContent() {
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            stringResource(R.string.what_is_zakat),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(stringResource(R.string.learn_what_is_zakat_content))
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            stringResource(R.string.what_is_the_purpose),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(stringResource(R.string.learn_purpose_content))
    }
}

@Composable
fun WhoToGiveContent() {
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            stringResource(R.string.who_pays_zakat),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(stringResource(R.string.learn_who_pays_intro))
        Text(stringResource(R.string.learn_who_pays_1))
        Text(stringResource(R.string.learn_who_pays_2))
        Text(stringResource(R.string.learn_who_pays_3))
        Text(stringResource(R.string.learn_who_pays_4))
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            stringResource(R.string.who_receives),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(stringResource(R.string.learn_who_receives_content))
    }
}

@Composable
fun TypesOfZakatContent() {
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(stringResource(R.string.learn_types_intro))
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            stringResource(R.string.zakat_fitrah),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(stringResource(R.string.learn_zakat_fitrah_content))
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            stringResource(R.string.zakat_mal),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(stringResource(R.string.learn_zakat_mal_content))
    }
}

@Composable
fun DuaContent() {
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(stringResource(R.string.learn_dua_content))
        Spacer(modifier = Modifier.height(8.dp))
        Text(stringResource(R.string.learn_dua_note_content))
    }
}

@Preview(showBackground = true)
@Composable
fun LearnScreenPreview() {
    ZakatCalculatorTheme {
        LearnScreenContent()
    }
}
