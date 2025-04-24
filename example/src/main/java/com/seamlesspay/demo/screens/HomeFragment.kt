package com.seamlesspay.demo.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.seamlesspay.demo.base.BaseFragment
import com.seamlesspay.demo.util.forceDarkMode
import com.seamlesspay.demo.util.isDarkMode
import com.seamlesspay.example.R
import com.seamlesspay.example.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
  override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
    get() = FragmentHomeBinding::inflate

  override fun FragmentHomeBinding.initViews() {
    // Set up Theme Switching

    val isDarkMode = requireContext().isDarkMode()
    switchDarkMode.isChecked = isDarkMode
    updateSwitchTitle(isDarkMode)
    switchDarkMode.setOnCheckedChangeListener { _, b ->
      forceDarkMode(b)
      updateSwitchTitle(b)
    }

    // Set up Controls

    rowGooglePay.setOnClickListener {
      findNavController().navigate(R.id.actionGooglePayFragment)
    }

    rowCardForm.setOnClickListener {
      findNavController().navigate(R.id.actionTransactionSelectionFragment)
    }
  }

  private fun updateSwitchTitle(darkMode: Boolean) {
    binding.textDarkModeLabel.text = if (darkMode) {
      getString(R.string.light_mode_title)
    } else {
      getString(R.string.dark_mode_title)
    }
  }
}