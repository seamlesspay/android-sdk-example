package com.seamlesspay.demo.util

import java.text.NumberFormat
import java.util.Locale

/**
 * Converts an integer representing a currency subunit (e.g., cents)
 * to a formatted currency string (e.g., dollars).
 *
 * Example:
 * ```kotlin
 * val cents = 100
 * println(cents.toCurrencyString()) // $1.00
 * ```
 *
 * @param locale The locale used for currency formatting. Default is US.
 * @param currencyCode Optional ISO 4217 currency code (e.g., "USD", "EUR").
 *        If not provided, the default currency for the locale is used.
 *
 * @return A string formatted as a currency amount.
 */
fun Int.toCurrencyString(locale: Locale = Locale.US, currencyCode: String = "USD"): String {
  val numberFormat = NumberFormat.getCurrencyInstance(locale)
  numberFormat.currency = java.util.Currency.getInstance(currencyCode)
  return numberFormat.format(this / 100.0)
}