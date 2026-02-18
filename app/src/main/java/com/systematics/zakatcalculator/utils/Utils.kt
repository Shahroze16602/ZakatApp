package com.systematics.zakatcalculator.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
import com.systematics.zakatcalculator.R

object Utils {
    fun searchOnWeb(context: Context, query: String) {
        if (query.isBlank()) return

        val encodedQuery = Uri.encode(query)
        val webUri = context.getString(R.string.web_search_url_template, encodedQuery).toUri()

        val webIntent = Intent(Intent.ACTION_VIEW, webUri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        try {
            context.startActivity(webIntent)
        } catch (_: ActivityNotFoundException) {
            // No compatible browser installed.
        }
    }
}
