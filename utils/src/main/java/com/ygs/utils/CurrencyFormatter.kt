package com.ygs.utils


import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

fun formatCurrency(amount: Double, locale: Locale = Locale.getDefault()): String {
    val currencyFormat = NumberFormat.getCurrencyInstance(locale) as DecimalFormat
    val decimalFormatSymbols = currencyFormat.decimalFormatSymbols.apply {
        currencySymbol = Currency.getInstance(Locale.US).symbol
    }
    currencyFormat.decimalFormatSymbols = decimalFormatSymbols

    return currencyFormat.format(amount)
}