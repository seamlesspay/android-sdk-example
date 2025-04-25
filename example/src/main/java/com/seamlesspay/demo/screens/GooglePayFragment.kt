package com.seamlesspay.demo.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.gms.wallet.button.ButtonConstants
import com.google.android.gms.wallet.button.ButtonConstants.ButtonTheme.DARK
import com.google.android.gms.wallet.button.ButtonConstants.ButtonType.PAY
import com.google.android.gms.wallet.button.ButtonOptions
import com.seamlesspay.api.error.ApiError
import com.seamlesspay.api.handler.GooglePayHandler
import com.seamlesspay.api.models.ClientConfiguration
import com.seamlesspay.api.utils.GooglePayJsonHelper
import com.seamlesspay.demo.base.BaseFragment
import com.seamlesspay.demo.config.GlobalConfiguration.clientConfiguration
import com.seamlesspay.demo.model.PaymentType
import com.seamlesspay.demo.model.ResultInfo
import com.seamlesspay.demo.model.ResultType
import com.seamlesspay.demo.util.getSubunitValue
import com.seamlesspay.demo.util.hideKeyboard
import com.seamlesspay.demo.util.setupCurrencyInput
import com.seamlesspay.demo.util.toFancyString
import com.seamlesspay.example.R
import com.seamlesspay.example.databinding.FragmentGooglePayBinding
import com.seamlesspay.ui.common.GooglePayCallback
import com.seamlesspay.ui.models.ChargeRequest
import com.seamlesspay.ui.models.PaymentResponse
import org.json.JSONArray

class GooglePayFragment : BaseFragment<FragmentGooglePayBinding>() {
  override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGooglePayBinding
    get() = FragmentGooglePayBinding::inflate

  private lateinit var googlePyaHandler: GooglePayHandler

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    googlePyaHandler = GooglePayHandler(this, clientConfiguration)
  }

  override fun FragmentGooglePayBinding.initViews() {
    // Set up Google Pay Button

    if (googlePyaHandler.isReadyToPay()) {
      piGooglePay.isVisible = false
        payButton.isVisible = true
    }

    payButton.initialize(
      ButtonOptions
        .newBuilder()
        .setButtonType(PAY)
        .setAllowedPaymentMethods(
          JSONArray().put(GooglePayJsonHelper.createCardPaymentMethod(isForPaymentDataRequest = false))
            .toString()
        )
        .build()
    )

    payButton.setOnClickListener {
      etAmount.hideKeyboard()
      initializePayment()
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

    // Set Up Controls

    googlePyaHandler.setProcessingCallback {
      layoutProgress.root.isVisible = it
    }
    googlePyaHandler.setIsReadyToPayCallback { isReadyToPay ->
      piGooglePay.isVisible = false
      if (isReadyToPay) {
        payButton.isVisible = true
      } else {
        tvPayUnavailable.isVisible = true
      }
    }

    // Set Up Edit Text

    etAmount.setupCurrencyInput()
  }

  private fun initializePayment() {

    googlePyaHandler.presentGooglePayFor(
      ChargeRequest(amount = binding.etAmount.getSubunitValue()),
      object : GooglePayCallback {
        override fun success(paymentResponse: PaymentResponse) {
          navigateNext(paymentResponse.toFancyString(), ResultType.Success)
        }

        override fun failure(apiError: ApiError?) {
          navigateNext(apiError.toFancyString(), ResultType.Error)
        }

        override fun cancel() {
          showSnackbar(binding.root, R.string.google_pay_payment_canceled)
        }
      })
  }

  private fun navigateNext(result: String, resultType: ResultType) {
    findNavController().navigate(
      R.id.actionResultFragment, bundleOf(
        "resultInfo" to ResultInfo(
          resultType = resultType,
          result = result,
          paymentType = PaymentType.Button
        )
      )
    )
  }
}