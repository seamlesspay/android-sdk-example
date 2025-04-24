package com.seamlesspay.demo.util

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate

/**
 * Checks whether the current [Context] is in dark mode.
 *
 * This function evaluates the system's UI mode configuration to determine if dark mode is enabled.
 *
 * @return `true` if the system is currently using dark mode, `false` otherwise.
 */
fun Context.isDarkMode(): Boolean {
  return when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
    Configuration.UI_MODE_NIGHT_YES -> true
    Configuration.UI_MODE_NIGHT_NO -> false
    Configuration.UI_MODE_NIGHT_UNDEFINED -> false
    else -> false
  }
}

/**
 * Forces the app to switch between light and dark mode.
 *
 * This uses [AppCompatDelegate.setDefaultNightMode] to apply the desired UI theme across the app.
 * The change will take effect immediately and persist across activities.
 *
 * @param dark If `true`, switches to dark mode. If `false`, switches to light mode.
 */
fun forceDarkMode(dark: Boolean) {
  AppCompatDelegate.setDefaultNightMode(
    if (dark) {
      AppCompatDelegate.MODE_NIGHT_YES
    } else {
      AppCompatDelegate.MODE_NIGHT_NO
    }
  )
}