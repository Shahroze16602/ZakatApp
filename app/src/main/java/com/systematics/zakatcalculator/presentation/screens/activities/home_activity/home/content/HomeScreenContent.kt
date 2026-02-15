package com.systematics.zakatcalculator.presentation.screens.activities.home_activity.home.content

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.systematics.zakatcalculator.R
import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.FitrahActivity
import com.systematics.zakatcalculator.presentation.screens.activities.gold_activity.GoldActivity
import com.systematics.zakatcalculator.presentation.screens.activities.home_activity.learn.LearnScreen
import com.systematics.zakatcalculator.presentation.screens.activities.home_activity.settings.SettingsScreen
import com.systematics.zakatcalculator.presentation.screens.activities.income_activity.IncomeActivity
import com.systematics.zakatcalculator.presentation.screens.activities.rikaz_activity.RikazActivity
import com.systematics.zakatcalculator.presentation.screens.activities.savings_activity.SavingsActivity
import com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.SilverActivity
import com.systematics.zakatcalculator.ui.theme.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.chrono.HijrahDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import java.util.*

@Composable
fun HomeScreenContent() {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = stringResource(R.string.home)) },
                    label = { Text(stringResource(R.string.home)) },
                    selected = pagerState.currentPage == 0,
                    onClick = { scope.launch { pagerState.animateScrollToPage(0) } },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Book, contentDescription = stringResource(R.string.learn)) },
                    label = { Text(stringResource(R.string.learn)) },
                    selected = pagerState.currentPage == 1,
                    onClick = { scope.launch { pagerState.animateScrollToPage(1) } },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = stringResource(R.string.settings)) },
                    label = { Text(stringResource(R.string.settings)) },
                    selected = pagerState.currentPage == 2,
                    onClick = { scope.launch { pagerState.animateScrollToPage(2) } },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(innerPadding),
            userScrollEnabled = false // Usually bottom nav pagers have swipe disabled or controlled
        ) { page ->
            when (page) {
                0 -> HomeTabContent()
                1 -> LearnScreen()
                2 -> SettingsScreen()
            }
        }
    }
}

@Composable
fun HomeTabContent() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        HeaderSection()
        Spacer(modifier = Modifier.height(16.dp))
//        WelcomeCard()
//        Spacer(modifier = Modifier.height(16.dp))
        CategoryGrid(onCategoryClick = { category ->
            val intent = when (category.nameRes) {
                R.string.cat_fitrah -> Intent(context, FitrahActivity::class.java)
                R.string.cat_gold -> Intent(context, GoldActivity::class.java)
                R.string.cat_silver -> Intent(context, SilverActivity::class.java)
                R.string.cat_savings -> Intent(context, SavingsActivity::class.java)
                R.string.cat_income -> Intent(context, IncomeActivity::class.java)
                R.string.cat_rikaz -> Intent(context, RikazActivity::class.java)
                else -> null
            }
            intent?.let { context.startActivity(it) }
        })
        Spacer(modifier = Modifier.height(16.dp))
//        ManageAssetsCard()
//        Spacer(modifier = Modifier.height(16.dp))
        VerseOfTheDayCard()
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun HeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
            )
            .padding(24.dp)
    ) {
        Column {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.calculate_with_ease),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(24.dp))
            DateCard()
        }
    }
}

@Composable
fun DateCard() {
    val today = LocalDate.now()
    val hijrahDate = HijrahDate.from(today)
    val hijrahFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH)
    val gregorianFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = hijrahDate.get(ChronoField.DAY_OF_MONTH).toString(),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${hijrahDate.format(hijrahFormatter)} AH",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = today.format(gregorianFormatter),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun WelcomeCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.welcome),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(R.string.zakat_done_this_year),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(stringResource(R.string.view_details), color = MaterialTheme.colorScheme.primary)
                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Icon(
                    Icons.Default.CardGiftcard,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f)
                )
            }
        }
    }
}

data class CategoryItemData(val nameRes: Int, val icon: ImageVector)

@Composable
fun CategoryGrid(onCategoryClick: (CategoryItemData) -> Unit) {
    val categories = listOf(
        CategoryItemData(R.string.cat_fitrah, Icons.Default.ShoppingBag),
        CategoryItemData(R.string.cat_gold, Icons.Default.BrightnessHigh),
        CategoryItemData(R.string.cat_silver, Icons.Default.BrightnessMedium),
        CategoryItemData(R.string.cat_savings, Icons.Default.AccountBalanceWallet),
        CategoryItemData(R.string.cat_income, Icons.Default.MonetizationOn),
        CategoryItemData(R.string.cat_rikaz, Icons.Default.Inventory2)
    )

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CategoryItem(categories[0], Modifier.weight(1f), onClick = { onCategoryClick(categories[0]) })
            CategoryItem(categories[1], Modifier.weight(1f), onClick = { onCategoryClick(categories[1]) })
            CategoryItem(categories[2], Modifier.weight(1f), onClick = { onCategoryClick(categories[2]) })
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CategoryItem(categories[3], Modifier.weight(1f), onClick = { onCategoryClick(categories[3]) })
            CategoryItem(categories[4], Modifier.weight(1f), onClick = { onCategoryClick(categories[4]) })
            CategoryItem(categories[5], Modifier.weight(1f), onClick = { onCategoryClick(categories[5]) })
        }
    }
}

@Composable
fun CategoryItem(category: CategoryItemData, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .aspectRatio(1f),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                category.icon,
                contentDescription = stringResource(category.nameRes),
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(category.nameRes),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ManageAssetsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.manage_assets),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(R.string.manage_assets_desc),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.zakat_al_mal_progress),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                    )
                    LinearProgressIndicator(
                        progress = { 0.3f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        trackColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f),
                    )
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(stringResource(R.string.view_details), color = MaterialTheme.colorScheme.primary)
                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Icon(
                    Icons.Default.Wallet,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f)
                )
            }
        }
    }
}

@Composable
fun VerseOfTheDayCard() {
    val verses = listOf(
        stringResource(R.string.verse_1) to stringResource(R.string.verse_1_ref),
        stringResource(R.string.verse_2) to stringResource(R.string.verse_2_ref),
        stringResource(R.string.verse_3) to stringResource(R.string.verse_3_ref)
    )

    // Simple logic to select a verse based on the day
    val dayOfYear = LocalDate.now().dayOfYear
    val verse = verses[dayOfYear % verses.size]

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Star, contentDescription = null, tint = MaterialTheme.colorScheme.onTertiary)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.zakat_verse_day),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "\"${verse.first}\"",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = verse.second,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ZakatCalculatorTheme {
        HomeScreenContent()
    }
}
