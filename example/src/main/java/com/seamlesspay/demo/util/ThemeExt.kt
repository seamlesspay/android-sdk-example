package com.seamlesspay.demo.util

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate

fun Context.isDarkMode(): Boolean {
  return when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
    Configuration.UI_MODE_NIGHT_YES -> true
    Configuration.UI_MODE_NIGHT_NO -> false
    Configuration.UI_MODE_NIGHT_UNDEFINED -> false
    else -> false
  }
}

fun forceDarkMode(dark: Boolean) {
  AppCompatDelegate.setDefaultNightMode(
    if (dark) {
      AppCompatDelegate.MODE_NIGHT_YES
    } else {
      AppCompatDelegate.MODE_NIGHT_NO
    }
  )
}