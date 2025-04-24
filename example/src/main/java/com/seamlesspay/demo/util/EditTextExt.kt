package com.seamlesspay.demo.util

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * Extension function for [EditText] to automatically format user input as currency.
 *
 * As the user types, the input is reformatted into a standard currency format with
 * two decimal places (e.g., "1234" becomes "12.34"). Only digits are accepted; all
 * other characters are stripped.
 *
 * Example:
 * ```
 * editText.setupCurrencyInput()
 * ```
 *
 * Note: This does not add currency symbols, only numeric formatting.
 */
fun EditText.setupCurrencyInput() {
  var currentText = ""
  this.addTextChangedListener(object : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
      if (s.toString() != currentText) {
        this@setupCurrencyInput.removeTextChangedListener(this)

        val cleanString = s.toString().replace("[^\\d]".toRegex(), "")
        val parsed = if (cleanString.isNotEmpty()) cleanString.toLong() else 0L
        val formatted = "%.2f".format(parsed / 100.0)

        currentText = formatted
        this@setupCurrencyInput.setText(formatted)
        this@setupCurrencyInput.setSelection(formatted.length)

        this@setupCurrencyInput.addTextChangedListener(this)
      }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
  })
}

/**
 * Retrieves the numeric value of the [EditText] as subunits (e.g., cents).
 *
 * This function assumes the text is formatted with two decimal places and converts
 * it to an integer representing the value in subunits (e.g., "12.34" becomes 1234).
 *
 * @return Integer value in subunits, or 0 if parsing fails.
 */
fun EditText.getSubunitValue(): Int {
  val textStr = this.text.toString()
  val value = textStr.toDoubleOrNull() ?: 0.0
  return (value * 100).toInt()
}

/**
 * Extension function to hide the soft keyboard from a [View].
 *
 * This can be called from any view (such as an [EditText] or [Button]) to dismiss
 * the keyboard when it is no longer needed.
 *
 * Example:
 * ```
 * button.setOnClickListener {
 *     it.hideKeyboard()
 * }
 * ```
 */
fun View.hideKeyboard() {
  val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  imm.hideSoftInputFromWindow(windowToken, 0)
}
