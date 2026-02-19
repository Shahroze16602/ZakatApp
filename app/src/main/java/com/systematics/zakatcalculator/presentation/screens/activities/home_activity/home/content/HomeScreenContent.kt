package com.systematics.zakatcalculator.presentation.screens.activities.home_activity.home.content

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.systematics.zakatcalculator.R
import com.systematics.zakatcalculator.presentation.screens.activities.fitrah_activity.FitrahActivity
import com.systematics.zakatcalculator.presentation.screens.activities.gold_activity.GoldActivity
import com.systematics.zakatcalculator.presentation.screens.activities.home_activity.learn.LearnScreen
import com.systematics.zakatcalculator.presentation.screens.activities.home_activity.settings.SettingsScreen
import com.systematics.zakatcalculator.presentation.screens.activities.income_activity.IncomeActivity
import com.systematics.zakatcalculator.presentation.screens.activities.rikaz_activity.RikazActivity
import com.systematics.zakatcalculator.presentation.screens.activities.savings_activity.SavingsActivity
import com.systematics.zakatcalculator.presentation.screens.activities.silver_activity.SilverActivity
import com.systematics.zakatcalculator.ui.theme.ZakatCalculatorTheme
import com.systematics.zakatcalculator.utils.ZakatPreferences
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.chrono.HijrahDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import java.util.Locale

@Composable
fun HomeScreenContent() {
    val context = LocalContext.current
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()
    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler {
        if (pagerState.currentPage != 0) {
            scope.launch { pagerState.animateScrollToPage(0) }
        } else {
            showExitDialog = true
        }
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = {
                Text(
                    text = stringResource(R.string.exit_dialog_title),
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.exit_dialog_message),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    showExitDialog = false
                    (context as? Activity)?.finish()
                }) {
                    Text(
                        text = stringResource(R.string.exit),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text(
                        text = stringResource(R.string.cancel),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = stringResource(R.string.home)
                        )
                    },
                    label = { Text(stringResource(R.string.home)) },
                    selected = pagerState.currentPage == 0,
                    onClick = { scope.launch { pagerState.animateScrollToPage(0) } },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.Book,
                            contentDescription = stringResource(R.string.learn)
                        )
                    },
                    label = { Text(stringResource(R.string.learn)) },
                    selected = pagerState.currentPage == 1,
                    onClick = { scope.launch { pagerState.animateScrollToPage(1) } },
                    colors = NavigationBarItemDefaults.colors(

                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = stringResource(R.string.settings)
                        )
                    },
                    label = { Text(stringResource(R.string.settings)) },
                    selected = pagerState.currentPage == 2,
                    onClick = { scope.launch { pagerState.animateScrollToPage(2) } },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }
    ) { innerPadding ->

        val layoutDirection = LocalLayoutDirection.current

        val adjustedPadding = PaddingValues(
            top = 0.dp,
            bottom = innerPadding.calculateBottomPadding(),
            start = innerPadding.calculateStartPadding(layoutDirection = layoutDirection),
            end = innerPadding.calculateEndPadding(layoutDirection = layoutDirection)
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(adjustedPadding),
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> HomeTabContent()
                1 -> LearnScreen(onBackClick = { scope.launch { pagerState.animateScrollToPage(0) } })
                2 -> SettingsScreen(onBackClick = { scope.launch { pagerState.animateScrollToPage(0) } })
            }
        }
    }
}

@Composable
fun HomeTabContent() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var categoryStatuses by remember { mutableStateOf(loadCategoryStatuses(context)) }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                categoryStatuses = loadCategoryStatuses(context)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

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
        CategoryGrid(
            statuses = categoryStatuses,
            onCategoryClick = { category ->
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
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
//        ManageAssetsCard()
//        Spacer(modifier = Modifier.height(16.dp))
        VerseOfTheDayCard()
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun HeaderSection() {
    Box{
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                )
                .padding(24.dp)
                .statusBarsPadding()
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
        Image(
            modifier = Modifier.align(Alignment.CenterEnd).height(250.dp),
            contentScale = ContentScale.FillHeight,
            painter = painterResource(id = R.drawable.image_pattern),
            contentDescription = null,
        )
    }
}

@Composable
fun DateCard() {
    val today = LocalDate.now()
    val hijrahDate = HijrahDate.from(today)
    val hijrahFormatter = DateTimeFormatter.ofPattern(
        stringResource(R.string.date_pattern_hijrah_month_year),
        Locale.ENGLISH
    )
    val gregorianFormatter = DateTimeFormatter.ofPattern(
        stringResource(R.string.date_pattern_gregorian_day_month_year),
        Locale.ENGLISH
    )

    Card(
        modifier = Modifier.fillMaxWidth(0.6f),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary.copy(
                alpha = 0.15f
            )
        ),
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
                text = stringResource(
                    R.string.hijrah_date_with_suffix,
                    hijrahDate.format(hijrahFormatter)
                ),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.SemiBold
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .height(1.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                Color.Transparent, Color.White.copy(0.3f), Color.Transparent
                            )
                        )
                    )
            )
            Text(
                text = today.format(gregorianFormatter),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
            )
        }
    }
}

/*@Composable
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
                        Text(
                            stringResource(R.string.view_details),
                            color = MaterialTheme.colorScheme.primary
                        )
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
}*/

data class CategoryItemData(val nameRes: Int, val iconRes: Int, val prefPrefix: String)
data class CategoryStatus(val isQualified: Boolean, val isFulfilled: Boolean)

@Composable
fun CategoryGrid(
    statuses: Map<Int, CategoryStatus>,
    onCategoryClick: (CategoryItemData) -> Unit
) {
    val categories = listOf(
        CategoryItemData(R.string.cat_fitrah, R.drawable.img_fitrah, ZakatPreferences.FITRAH_PREFIX),
        CategoryItemData(R.string.cat_gold, R.drawable.img_gold, ZakatPreferences.GOLD_PREFIX),
        CategoryItemData(R.string.cat_silver, R.drawable.img_silver, ZakatPreferences.SILVER_PREFIX),
        CategoryItemData(R.string.cat_savings, R.drawable.img_savings, ZakatPreferences.SAVINGS_PREFIX),
        CategoryItemData(R.string.cat_income, R.drawable.img_income, ZakatPreferences.INCOME_PREFIX),
        CategoryItemData(R.string.cat_rikaz, R.drawable.img_rikaz, ZakatPreferences.RIKAZ_PREFIX)
    )

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CategoryItem(
                category = categories[0],
                modifier = Modifier.weight(1f),
                status = statuses[categories[0].nameRes],
                onClick = { onCategoryClick(categories[0]) }
            )
            CategoryItem(
                category = categories[1],
                modifier = Modifier.weight(1f),
                status = statuses[categories[1].nameRes],
                onClick = { onCategoryClick(categories[1]) }
            )
            CategoryItem(
                category = categories[2],
                modifier = Modifier.weight(1f),
                status = statuses[categories[2].nameRes],
                onClick = { onCategoryClick(categories[2]) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CategoryItem(
                category = categories[3],
                modifier = Modifier.weight(1f),
                status = statuses[categories[3].nameRes],
                onClick = { onCategoryClick(categories[3]) }
            )
            CategoryItem(
                category = categories[4],
                modifier = Modifier.weight(1f),
                status = statuses[categories[4].nameRes],
                onClick = { onCategoryClick(categories[4]) }
            )
            CategoryItem(
                category = categories[5],
                modifier = Modifier.weight(1f),
                status = statuses[categories[5].nameRes],
                onClick = { onCategoryClick(categories[5]) }
            )
        }
    }
}

@Composable
fun CategoryItem(
    category: CategoryItemData,
    modifier: Modifier = Modifier,
    status: CategoryStatus? = null,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .aspectRatio(1f),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            val badgeText = when {
                status?.isFulfilled == true -> stringResource(R.string.badge_fulfilled)
                status?.isQualified == true -> stringResource(R.string.badge_qualified)
                else -> null
            }
            if (badgeText != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = badgeText,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(category.iconRes),
                    contentDescription = stringResource(category.nameRes),
                    modifier = Modifier.size(50.dp),
                    tint = Color.Unspecified
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
}

/*@Composable
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
                        Text(
                            stringResource(R.string.view_details),
                            color = MaterialTheme.colorScheme.primary
                        )
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
}*/

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
            .padding(horizontal = 16.dp)
            .border(width = 2.dp, color = MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(24.dp)),
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
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.zakat_verse_day),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.quoted_text, verse.first),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = verse.second,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun loadCategoryStatuses(context: Context): Map<Int, CategoryStatus> {
    val prefs = ZakatPreferences(context)

    fun status(prefix: String): CategoryStatus {
        val fulfilled = prefs.getBoolean(ZakatPreferences.key(prefix, ZakatPreferences.KEY_PAID))
        val qualifiedFromRequirements = when (prefix) {
            ZakatPreferences.FITRAH_PREFIX -> {
                prefs.getBoolean(ZakatPreferences.key(prefix, "can_give_rice")) &&
                    prefs.getBoolean(ZakatPreferences.key(prefix, "has_excess_food"))
            }
            ZakatPreferences.GOLD_PREFIX,
            ZakatPreferences.SILVER_PREFIX,
            ZakatPreferences.SAVINGS_PREFIX -> {
                prefs.getBoolean(ZakatPreferences.key(prefix, "requirement_1")) &&
                    prefs.getBoolean(ZakatPreferences.key(prefix, "requirement_2")) &&
                    prefs.getBoolean(ZakatPreferences.key(prefix, "requirement_3"))
            }
            ZakatPreferences.INCOME_PREFIX -> {
                prefs.getBoolean(ZakatPreferences.key(prefix, "requirement_1")) &&
                    prefs.getBoolean(ZakatPreferences.key(prefix, "requirement_2")) &&
                    prefs.getBoolean(ZakatPreferences.key(prefix, "requirement_3")) &&
                    prefs.getBoolean(ZakatPreferences.key(prefix, "requirement_4"))
            }
            ZakatPreferences.RIKAZ_PREFIX -> {
                prefs.getBoolean(ZakatPreferences.key(prefix, "requirement_1"))
            }
            else -> false
        }
        val qualified = prefs.getBoolean(
            ZakatPreferences.key(prefix, ZakatPreferences.KEY_QUALIFIED),
            qualifiedFromRequirements
        )
        return CategoryStatus(isQualified = qualified, isFulfilled = fulfilled)
    }

    return mapOf(
        R.string.cat_fitrah to status(ZakatPreferences.FITRAH_PREFIX),
        R.string.cat_gold to status(ZakatPreferences.GOLD_PREFIX),
        R.string.cat_silver to status(ZakatPreferences.SILVER_PREFIX),
        R.string.cat_savings to status(ZakatPreferences.SAVINGS_PREFIX),
        R.string.cat_income to status(ZakatPreferences.INCOME_PREFIX),
        R.string.cat_rikaz to status(ZakatPreferences.RIKAZ_PREFIX)
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ZakatCalculatorTheme {
        HomeScreenContent()
    }
}
