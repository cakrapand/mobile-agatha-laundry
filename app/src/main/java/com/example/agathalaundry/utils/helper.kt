package com.example.agathalaundry.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

fun formatDate(inputDateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")

    val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("id", "ID"))
    outputFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta")

    return try {
        val date = inputFormat.parse(inputDateString)
        outputFormat.format(date)
    } catch (e: Exception) {
        // Invalid date string
        "Invalid Date"
    }
}

fun addDaysToDate(inputDateString: String, days: Int): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")

    return try {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val date = inputFormat.parse(inputDateString)
        calendar.time = date
        calendar.add(Calendar.DATE, days)
        val newDate = calendar.time

        // Format the new date using the existing formatDate function
        formatDate(inputFormat.format(newDate))
    } catch (e: Exception) {
        // Invalid date string
        "Invalid Date"
    }
}

fun formatToRupiah(amount: Long): String {
    val localeID = Locale("id", "ID")
    val numberFormat = NumberFormat.getCurrencyInstance(localeID)
    return numberFormat.format(amount)
}
