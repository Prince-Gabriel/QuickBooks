package com.example.quickbooks.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id"))

    fun formatDate(timestamp: Long): String {
        return try {
            dateFormat.format(Date(timestamp))
        } catch (e: Exception) {
            e.printStackTrace()
            "Invalid date"
        }
    }
}