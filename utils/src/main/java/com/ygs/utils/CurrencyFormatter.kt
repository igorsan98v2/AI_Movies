package com.ygs.utils


import java.text.NumberFormat
import java.util.Locale

fun formatCurrency(amount: Double, locale: Locale = Locale.getDefault()): String {
    val currencyFormat = NumberFormat.getCurrencyInstance(locale)
    return currencyFormat.format(amount)
}