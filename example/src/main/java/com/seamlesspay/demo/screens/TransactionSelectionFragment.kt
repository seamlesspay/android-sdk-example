package com.seamlesspay.demo.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.seamlesspay.demo.base.BaseFragment
import com.seamlesspay.demo.model.TransactionInfo
import com.seamlesspay.demo.model.TransactionType
import com.seamlesspay.demo.util.getSubunitValue
import com.seamlesspay.demo.util.hideKeyboard
import com.seamlesspay.demo.util.setupCurrencyInput
import com.seamlesspay.demo.util.setupSingleSelectionRadioButtons
import com.seamlesspay.example.R
import com.seamlesspay.example.databinding.FragmentTransactionSelectionBinding

class TransactionSelectionFragment : BaseFragment<FragmentTransactionSelectionBinding>() {
  override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTransactionSelectionBinding
    get() = FragmentTransactionSelectionBinding::inflate

  override fun FragmentTransactionSelectionBinding.initViews() {
    // Set Up Controls

    llTransactionType.setupSingleSelectionRadioButtons {
      validate()
    }

    btnContinue.setOnClickListener {
      etAmount.hideKeyboard()
      val transactionType = when {
        rbTokenize.isChecked -> TransactionType.Tokenize
        rbCharge.isChecked -> TransactionType.Charge
        else -> TransactionType.Refund
      }
      val transactionInfo = TransactionInfo(
        transactionType = transactionType,
        amount = etAmount.getSubunitValue()
      )

      // TODO navigate to card form view with collected data
    }

    // Set Up Toolbar

    topAppBar.setNavigationOnClickListener {
      findNavController().popBackStack()
    }

    topAppBar.setOnMenuItemClickListener {
      when (it.itemId) {
        R.id.action_close -> {
          findNavController().popBackStack(R.id.homeFragment, false)
          true
        }

        else -> false
      }
    }

    // Set Up Edit Text

    etAmount.setupCurrencyInput()
  }

  private fun validate() {
    binding.tiAmount.isVisible = !binding.rbTokenize.isChecked
    binding.etAmount.text?.clear()
  }
}