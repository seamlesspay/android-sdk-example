package com.seamlesspay.demo.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.seamlesspay.demo.base.BaseFragment
import com.seamlesspay.demo.model.PaymentType
import com.seamlesspay.demo.model.ResultType
import com.seamlesspay.demo.util.copyToClipboard
import com.seamlesspay.example.R
import com.seamlesspay.example.databinding.FragmentResultBinding

class ResultFragment : BaseFragment<FragmentResultBinding>() {
  override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentResultBinding
    get() = FragmentResultBinding::inflate

  private val navArgs: ResultFragmentArgs by navArgs()

  override fun FragmentResultBinding.initViews() {
    // Set Up Response Data

    when (navArgs.resultInfo.resultType) {
      ResultType.Success -> tvSuccess.isVisible = true
      ResultType.Error -> tvError.isVisible = true
    }

    topAppBar.title = when(navArgs.resultInfo.paymentType) {
      PaymentType.Form -> getString(R.string.card_form_title)
      PaymentType.Button -> getString(R.string.google_pay_button_title)
    }

    tvContent.text = navArgs.resultInfo.result

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

    // Set Up Controls

    btnCopy.setOnClickListener {
      requireContext().copyToClipboard(
        label = getString(R.string.response_code_title),
        text = navArgs.resultInfo.result
      )
    }
  }
}