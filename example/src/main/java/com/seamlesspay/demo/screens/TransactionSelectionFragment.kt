package com.seamlesspay.demo.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
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
  private var transactionType = TransactionType.Tokenize

  override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTransactionSelectionBinding
    get() = FragmentTransactionSelectionBinding::inflate

  override fun FragmentTransactionSelectionBinding.initViews() {
    // Set Up Controls

    when(transactionType) {
      TransactionType.Tokenize -> rbTokenize.isChecked = true
      TransactionType.Charge -> rbCharge.isChecked = true
      TransactionType.Refund -> rbRefund.isChecked = true
    }

    llTransactionType.setupSingleSelectionRadioButtons {
      validate()
    }

    btnContinue.setOnClickListener {
      etAmount.hideKeyboard()
      val transactionInfo = TransactionInfo(
        transactionType = transactionType,
        amount = etAmount.getSubunitValue()
      )

      findNavController().navigate(
        R.id.actionCardFormFragment,
        bundleOf("transactionInfo" to transactionInfo)
      )
    }

    // Set Up Toolbar

    topAppBar.setNavigationOnClickListener {
      findNavController().popBackStack()
    }

    // Set Up Edit Text

    etAmount.setupCurrencyInput()
  }

  private fun validate() {
    transactionType = when {
      binding.rbCharge.isChecked -> TransactionType.Charge
      binding.rbRefund.isChecked -> TransactionType.Refund
      else -> TransactionType.Tokenize
    }
    binding.tiAmount.isVisible = transactionType != TransactionType.Tokenize
  }
}