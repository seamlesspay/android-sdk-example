package com.seamlesspay.demo.util

import android.view.View
import android.view.ViewGroup
import com.google.android.material.radiobutton.MaterialRadioButton

fun ViewGroup.setupSingleSelectionRadioButtons(validate: () -> Unit) {
  val radioButtons = mutableListOf<MaterialRadioButton>()

  // Traverse each direct child (assumed to be layouts containing the radio buttons)
  for (i in 0 until childCount) {
    val child = getChildAt(i)
    val radio = findRadioButton(child)
    if (radio != null) {
      radioButtons.add(radio)
      child.setOnClickListener {
        radioButtons.forEach { it.isChecked = false }
        radio.isChecked = true
        validate()
      }
    }
  }
}

// Helper function to recursively find the first MaterialRadioButton in a view tree
private fun findRadioButton(view: View): MaterialRadioButton? {
  if (view is MaterialRadioButton) return view
  if (view is ViewGroup) {
    for (i in 0 until view.childCount) {
      val result = findRadioButton(view.getChildAt(i))
      if (result != null) return result
    }
  }
  return null
}