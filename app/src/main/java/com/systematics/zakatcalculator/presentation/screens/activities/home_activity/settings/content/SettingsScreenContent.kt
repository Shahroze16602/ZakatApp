package com.systematics.zakatcalculator.presentation.screens.activities.home_activity.settings.content

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.systematics.zakatcalculator.BuildConfig
import com.systematics.zakatcalculator.R
import com.systematics.zakatcalculator.ui.theme.ZakatCalculatorTheme
import androidx.core.net.toUri
import com.systematics.zakatcalculator.presentation.screens.components.CommonAppBar

@Composable
fun SettingsScreenContent(onBackClick: () -> Unit = {}) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {
        CommonAppBar(
            title = stringResource(R.string.settings),
            onBackClick = onBackClick
        )
        Spacer(modifier = Modifier.height(24.dp))
        SettingsList(context)
    }
}

@Composable
fun SettingsList(context: Context) {
    val items = listOf(
        SettingsItemData(stringResource(R.string.rate_us), Icons.Default.StarRate) { rateUs(context) },
        SettingsItemData(stringResource(R.string.share_app), Icons.Default.Share) { shareApp(context) },
        SettingsItemData(stringResource(R.string.privacy_policy), Icons.Default.PrivacyTip) { openPrivacyPolicy(context) },
        SettingsItemData(stringResource(R.string.app_version), Icons.Default.Info, BuildConfig.VERSION_NAME)
    )

    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items.forEach { item ->
            SettingsItemCard(item)
        }
    }
}

data class SettingsItemData(val title: String, val icon: ImageVector, val value: String? = null, val onClick: (() -> Unit)? = null)

@Composable
fun SettingsItemCard(item: SettingsItemData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = item.onClick != null) { item.onClick?.invoke() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
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
            if (item.value != null) {
                Text(
                    text = item.value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

private fun rateUs(context: Context) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        context.getString(R.string.rate_us_market_url, context.packageName).toUri()
    )
    context.startActivity(intent)
}

private fun shareApp(context: Context) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, context.getString(R.string.share_app_text))
        type = context.getString(R.string.mime_text_plain)
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}

private fun openPrivacyPolicy(context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, context.getString(R.string.privacy_policy_url).toUri())
    context.startActivity(intent)
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    ZakatCalculatorTheme {
        SettingsScreenContent()
    }
}
