package com.seamlesspay.demo.util

import com.seamlesspay.api.error.ApiError
import com.seamlesspay.ui.models.PaymentResponse
import com.seamlesspay.ui.models.TokenizeResponse

fun PaymentResponse.toFancyString(): String {
  val sb = StringBuilder()
  sb.appendLine("Payment Token: $paymentToken")

  when (val detail = details) {
    is PaymentResponse.Details.CreditCard -> {
      sb.appendLine("Credit Card Info:")
      detail.cardBrand?.type?.let { sb.appendLine("\t• Card Brand: $it") }
      detail.lastFour?.let { sb.appendLine("\t• Last 4 Digits: **** **** **** $it") }
      detail.expDate?.let { sb.appendLine("\t• Expiration Date: $it") }
      detail.accountType?.let { sb.appendLine("\t• Account Type: $it") }
      detail.currency?.let { sb.appendLine("\t• Currency: $it") }
      detail.amount?.let { sb.appendLine("\t• Amount: ${it / 100.0}") }
      detail.tip?.let { sb.appendLine("\t• Tip: ${it / 100.0}") }
      detail.surchargeFeeAmount?.let { sb.appendLine("\t• Surcharge Fee: ${it / 100.0}") }
      detail.transactionDate?.let { sb.appendLine("\t• Transaction Date: $it") }
      detail.authCode?.let { sb.appendLine("\t• Auth Code: $it") }
      detail.status?.let { sb.appendLine("\t• Status: $it") }
      detail.statusCode?.let { sb.appendLine("\t• Status Code: $it") }
      detail.statusDescription?.let { sb.appendLine("\t• Description: $it") }
      detail.avsPostalCodeResult?.let {
        sb.appendLine("\t• AVS Postal Code: ${it.name.replaceFirstChar { c -> c.uppercaseChar() }}")
      }
      detail.avsStreetAddressResult?.let {
        sb.appendLine("\t• AVS Street Address: ${it.name.replaceFirstChar { c -> c.uppercaseChar() }}")
      }
      detail.cvvResult?.let {
        sb.appendLine("\t• CVV Result: ${it.name.replaceFirstChar { c -> c.uppercaseChar() }}")
      }
      detail.batchId?.let { sb.appendLine("\t• Batch ID: $it") }
      detail.id?.let { sb.appendLine("\t• Transaction ID: $it") }
    }
  }

  return sb.toString()
}


fun TokenizeResponse.toFancyString(): String {
  val sb = StringBuilder()
  sb.appendLine("Payment Token: $paymentToken")

  when (val detail = details) {
    is TokenizeResponse.Details.CreditCard -> {
      sb.appendLine("Credit Card Info:")
      detail.paymentNetwork?.let { sb.appendLine("\t• Payment Network: $it") }
      detail.name?.let { sb.appendLine("\t• Cardholder Name: $it") }
      detail.expDate?.let { sb.appendLine("\t• Expiration Date: $it") }
      detail.lastFour?.let { sb.appendLine("\t• Last 4 Digits: **** **** **** $it") }
      detail.avsPostalCodeResult?.let {
        sb.appendLine("\t• AVS Postal Code Result: ${it.name.replaceFirstChar { c -> c.uppercaseChar() }}")
      }
      detail.avsStreetAddressResult?.let {
        sb.appendLine("\t• AVS Street Address Result: ${it.name.replaceFirstChar { c -> c.uppercaseChar() }}")
      }
      detail.cvvResult?.let {
        sb.appendLine("\t• CVV Result: ${it.name.replaceFirstChar { c -> c.uppercaseChar() }}")
      }
    }
  }

  return sb.toString()
}

fun ApiError?.toFancyString(): String {
  if (this == null) return "No API error."

  val sb = StringBuilder()
  val errorType = this::class.simpleName
    ?.removeSuffix("ApiError")
    ?.replaceFirstChar { it.uppercaseChar() }
    ?: "Unknown"

  sb.appendLine("Error Type: $errorType")
  sb.appendLine("\t• Status Code: $statusCode")
  sb.appendLine("\t• Description: $statusDescription")

  if (errors.isNotEmpty()) {
    sb.appendLine("\t• Details:")
    errors.forEachIndexed { index, error ->
      val fieldInfo = error.fieldName?.let { " (field: $it)" } ?: ""
      sb.appendLine("\t   ${index + 1}. ${error.message}$fieldInfo")
    }
  } else {
    sb.appendLine("\t• No additional error details.")
  }

  return sb.toString()
}
