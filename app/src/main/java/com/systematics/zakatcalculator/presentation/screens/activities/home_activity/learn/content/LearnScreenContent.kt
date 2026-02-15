package com.systematics.zakatcalculator.presentation.screens.activities.home_activity.learn.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.systematics.zakatcalculator.R
import com.systematics.zakatcalculator.ui.theme.ZakatCalculatorTheme

@Composable
fun LearnScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {
        LearnHeader()
        Spacer(modifier = Modifier.height(24.dp))
        LearnList()
    }
}

@Composable
fun LearnHeader() {
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
            .padding(top = 24.dp, bottom = 24.dp, start = 16.dp, end = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { /* Handle back */ }) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.learn),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
        }
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
            .clickable { expanded = !expanded },
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
                    color = Color.DarkGray
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
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("What is Zakat?", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text("Zakat is a religious obligation for Muslims to donate a portion of their wealth to charitable causes. The word \"zakat\" literally means \"purification\". It is one of the Five Pillars of Islam and is essential for self-purification and helping others.")
        Spacer(modifier = Modifier.height(8.dp))
        Text("What is the purpose?", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text("Zakat is meant to purify Muslims’ property and soul from faults like greed, stinginess, and meanness. It can help reduce poverty, build social harmony, and encourage generosity. Muslims believe that paying zakat purifies, increases, and blesses their remaining wealth.")
    }
}

@Composable
fun WhoToGiveContent() {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Who pays Zakat?", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text("Muslims who meet the following criteria are required to pay zakat:")
        Text("• Sound mind: The person must be of sound mind and not have a disturbed mental condition.")
        Text("• Baligh: The person must have reached the age of puberty, which is marked by dreams for men and menstruation for women.")
        Text("• Wealth reaches Nisab: The person’s wealth must reach the predetermined minimum limit, known as the Nisab.")
        Text("• Assets reach Hawl: The person’s wealth must have reached the age of one Islamic year, as adjusted to the Hijri calendar.")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Who receives?", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text("Zakat is given to the poor, travelers in need, and other designated groups of people.")
    }
}

@Composable
fun TypesOfZakatContent() {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("There are two main categories of Zakat, namely:")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Zakat Fitrah", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text("Zakat fitrah is a charitable donation of food that Muslims are required to pay during the month of Ramadan and before the Eid prayer on 1 Shawwal. It is a mandatory annual payment for every Muslim, regardless of age, gender, or wealth.")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Zakat Mal", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text("Zakat al-mal commonly referred to as “zakat on wealth” or “alms-giving,” is paid on money, gold, and other valuable assets. Zakat al-mal is calculated as 2.5% of an individual’s wealth. Assets that have been owned for one Islamic year (hawl) are subject to zakat al-mal.")
    }
}

@Composable
fun DuaContent() {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("The dua to say when paying zakat is, اللَّهُمَّ اجْعَلْهَا مَغْنَمًا وَلَا تَجْعَلْهَا مَغْرَمًا which means “O Allah, make it a gain and do not make it a loss”. This is said by the Messenger of Allah, as narrated by Abū Hurayrah.")
        Spacer(modifier = Modifier.height(8.dp))
        Text("It is mandatory to make an intention to give zakat, either when organizing the payment or when making the payment. If a donation is made without the intention of zakat, it will not be counted as zakat.")
    }
}

@Preview(showBackground = true)
@Composable
fun LearnScreenPreview() {
    ZakatCalculatorTheme {
        LearnScreenContent()
    }
}
