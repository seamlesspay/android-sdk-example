package com.seamlesspay.demo.screens

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.seamlesspay.api.error.ApiError
import com.seamlesspay.demo.base.BaseFragment
import com.seamlesspay.demo.config.GlobalConfiguration.clientConfiguration
import com.seamlesspay.demo.model.TransactionType
import com.seamlesspay.demo.util.hideKeyboard
import com.seamlesspay.demo.util.toCurrencyString
import com.seamlesspay.example.R
import com.seamlesspay.example.databinding.FragmentCardFormBinding
import com.seamlesspay.ui.common.PaymentCallback
import com.seamlesspay.ui.common.TokenizeCallback
import com.seamlesspay.ui.models.ChargeRequest
import com.seamlesspay.ui.models.DisplayConfiguration
import com.seamlesspay.ui.models.FieldConfiguration
import com.seamlesspay.ui.models.FieldOptions
import com.seamlesspay.ui.models.PaymentResponse
import com.seamlesspay.ui.models.RefundRequest
import com.seamlesspay.ui.models.TokenizeResponse
import com.seamlesspay.ui.models.style.ColorPalette
import com.seamlesspay.ui.models.style.Colors
import com.seamlesspay.ui.models.style.ElevationLevel
import com.seamlesspay.ui.models.style.FieldColors
import com.seamlesspay.ui.models.style.Shadow
import com.seamlesspay.ui.models.style.Shapes
import com.seamlesspay.ui.models.style.StyleOptions
import com.seamlesspay.ui.models.style.ThemeColors
import com.seamlesspay.ui.models.style.Typography

class CardFormFragment : BaseFragment<FragmentCardFormBinding>() {

  // Tokenize and Payment callbacks
  private val tokenizeCallback: TokenizeCallback = object : TokenizeCallback {
    override fun success(tokenizeResponse: TokenizeResponse) {
      hideProgressBar()
    }


    override fun failure(apiError: ApiError?) {
      hideProgressBar()
    }
  }

  private val paymentCallback: PaymentCallback = object : PaymentCallback {
    override fun success(paymentResponse: PaymentResponse) {
      hideProgressBar()
    }

    override fun failure(apiError: ApiError?) {
      hideProgressBar()
    }
  }

  private val navArgs: CardFormFragmentArgs by navArgs()

  private val amount by lazy {
    navArgs.transactionInfo.amount
  }

  private val transactionType by lazy {
    navArgs.transactionInfo.transactionType
  }

  override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCardFormBinding
    get() = FragmentCardFormBinding::inflate

  override fun FragmentCardFormBinding.initViews() {
    // Set Up Controls

    btnContinue.setOnClickListener {
     cardForm.hideKeyboard()
      performTransaction()
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

    // Set Up Card Form

    //Set up custom styles for CardForm
    val styleOptions = StyleOptions(
      Colors(
        ColorPalette(
          ThemeColors(Color.RED, Color.BLUE, Color.MAGENTA),
          FieldColors(
            null,
            null,
            null,
            null,
            null,
            null
          ), Color.BLUE,
          Color.RED
        ),
        ColorPalette(
          ThemeColors(Color.RED, Color.BLUE, Color.MAGENTA), null, null,
          null
        )
      ), Shapes(50f, Shadow(ElevationLevel.Level2)),
      Typography(com.seamlesspay.R.font.roboto_regular, 1f), null
    )

    //Set up field options
    val fieldOptions = FieldOptions(
      FieldConfiguration(DisplayConfiguration.OPTIONAL),
      FieldConfiguration(DisplayConfiguration.REQUIRED)
    )

    //Init card form with you custom configurations
    cardForm.init(clientConfiguration, fieldOptions)

    // Set Up Title
    when(transactionType) {
      TransactionType.Tokenize -> tvAmount.isVisible = false
      TransactionType.Charge -> tvAmount.text = getString(R.string.charge_amount_title, amount.toCurrencyString())
      TransactionType.Refund -> tvAmount.text = getString(R.string.refund_amount_title, amount.toCurrencyString())
    }
  }

  private fun performTransaction() {
    when(transactionType) {
      TransactionType.Tokenize -> binding.cardForm.tokenize(tokenizeCallback)
      TransactionType.Charge -> binding.cardForm.charge(
        paymentRequest = ChargeRequest(amount = amount),
        callback = paymentCallback
      )
      TransactionType.Refund -> binding.cardForm.refund(
        refundRequest = RefundRequest(amount = amount),
        callback = paymentCallback
      )
    }
    showProgressBar()
  }

  private fun hideProgressBar() {
    binding.layoutProgress.root.isVisible = false
  }

  private fun showProgressBar() {
    binding.layoutProgress.root.isVisible = true
  }
}