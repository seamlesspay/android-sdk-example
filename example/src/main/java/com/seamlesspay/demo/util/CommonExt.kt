package com.seamlesspay.demo.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import com.seamlesspay.demo.config.GlobalConfiguration.clientConfiguration
import com.seamlesspay.example.BuildConfig
import com.seamlesspay.example.R

fun buildDebugInfo(context: Context): List<Pair<String, String>> {
  val packageManager = context.packageManager
  val packageName = context.packageName
  val packageInfo = packageManager.getPackageInfo(packageName, 0)
  val appVersion = packageInfo.versionName ?: "Unknown"

  // Use versionCode for pre-28, longVersionCode for 28+
  val buildNumber = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
    packageInfo.longVersionCode
  } else {
    @Suppress("DEPRECATION")
    packageInfo.versionCode.toLong()
  }

  val osVersion = "Android ${Build.VERSION.RELEASE} (SDK ${Build.VERSION.SDK_INT})"
  val deviceModel = "${Build.MANUFACTURER} ${Build.MODEL}"

  // Get screen size
  val displayMetrics = context.resources.displayMetrics
  val screenWidth = displayMetrics.widthPixels
  val screenHeight = displayMetrics.heightPixels
  val screenDensity = displayMetrics.densityDpi
  val screenSize = "${screenWidth}x$screenHeight @${screenDensity}dpi"

  return buildList {
    add(context.getString(R.string.diagnostic_environment) to clientConfiguration.environment)
    add(context.getString(R.string.diagnostic_client_secret) to clientConfiguration.bearer.maskMiddle())
    val accountId = clientConfiguration.proxyAccountId
    if (accountId != null) {
      add(context.getString(R.string.diagnostic_proxy_account_id) to clientConfiguration.proxyAccountId.maskMiddle())
    } else {
      add(context.getString(R.string.diagnostic_proxy_account_id) to "null")
    }
    add(context.getString(R.string.diagnostic_app_version) to appVersion)
    add(context.getString(R.string.diagnostic_build_number) to buildNumber.toString())
    add(context.getString(R.string.diagnostic_sdk_version) to BuildConfig.SDK_VERSION)
    add(context.getString(R.string.diagnostic_os_version) to osVersion)
    add(context.getString(R.string.diagnostic_device_model) to deviceModel)
    add(context.getString(R.string.diagnostic_screen_size) to screenSize)
  }
}

fun String.maskMiddle(): String {
  return if (this.length >= 11) {
    val start = this.take(7)
    val end = this.takeLast(4)
    "$start****$end"
  } else {
    this
  }
}

fun Context.copyToClipboard(label: String, text: String) {
  val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
  val clip = ClipData.newPlainText(label, text)
  clipboard.setPrimaryClip(clip)
}