package com.cardcompany.transactions.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun Long.formatAsString(pattern: String): String {
    val dateTimeFormatter = DateTimeFormatter.ofPattern(pattern)
    return dateTimeFormatter.format(LocalDate.ofEpochDay(this))
}