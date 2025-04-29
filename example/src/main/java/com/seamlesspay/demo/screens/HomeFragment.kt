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

    switchDarkMode.isChecked = requireContext().isDarkMode()
    switchDarkMode.setOnCheckedChangeListener { _, b ->
      forceDarkMode(b)
    }

    // Set up Controls

    rowGooglePay.setOnClickListener {
      findNavController().navigate(R.id.actionGooglePayFragment)
    }

    rowCardForm.setOnClickListener {
      findNavController().navigate(R.id.actionTransactionSelectionFragment)
    }

    //tvAppData.text = buildDebugInfo(requireContext(), "2.2.5-rc1")
  }
}