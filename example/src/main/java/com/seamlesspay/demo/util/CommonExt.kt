package com.seamlesspay.demo.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import com.seamlesspay.demo.config.GlobalConfiguration.clientConfiguration

fun buildDebugInfo(context: Context, sdkVersion: String): String {
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

  return buildString {
    appendLine("Environment: ${clientConfiguration.environment}")
    appendLine("Client Secret: ${clientConfiguration.bearer}")
    clientConfiguration.proxyAccountId?.let {
      appendLine("Proxy Account Id: $it")
    }
    appendLine("App Version: $appVersion")
    appendLine("Build Number: $buildNumber")
    appendLine("SDK Version: $sdkVersion")
    appendLine("OS Version: $osVersion")
    appendLine("Device Model: $deviceModel")
    appendLine("Screen Size: $screenSize")
  }
}

fun Context.copyToClipboard(label: String, text: String) {
  val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
  val clip = ClipData.newPlainText(label, text)
  clipboard.setPrimaryClip(clip)
}