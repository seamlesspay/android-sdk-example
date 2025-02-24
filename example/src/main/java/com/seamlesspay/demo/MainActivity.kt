package com.seamlesspay.demo

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.seamlesspay.api.error.ApiError
import com.seamlesspay.api.exceptions.InvalidArgumentException
import com.seamlesspay.api.models.ClientConfiguration
import com.seamlesspay.example.R
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
import com.seamlesspay.ui.paymentinputs.direct.CardForm

class MainActivity : AppCompatActivity() {

  // Main UI Components
  private lateinit var cardForm: CardForm
  private lateinit var infoView: TextView
  private lateinit var progressBar: ProgressBar


  // Tokenize and Payment callbacks
  private val tokenizeCallback: TokenizeCallback = object : TokenizeCallback {
    override fun success(tokenizeResponse: TokenizeResponse) {
      infoView.text = tokenizeResponse.toString()
      progressBar.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    override fun failure(apiError: ApiError?) {
      apiError?.let {
        infoView.text = "Error ${it.statusDescription}"
      }
      progressBar.visibility = View.GONE
    }
  }

  private val paymentCallback: PaymentCallback = object : PaymentCallback {
    override fun success(paymentResponse: PaymentResponse) {
      infoView.text = paymentResponse.toString()
      progressBar.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    override fun failure(apiError: ApiError?) {
      apiError?.let {
        infoView.text = "Error ${it.statusDescription}"
      }
      progressBar.visibility = View.GONE
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val payButton = findViewById<Button>(R.id.payButton)
    val tokenizeButton = findViewById<Button>(R.id.tokenizeButton)
    val refundButton = findViewById<Button>(R.id.refundButton)

    progressBar = findViewById(R.id.progress)
    infoView = findViewById(R.id.infoView)
    cardForm = findViewById(R.id.cardForm)

    initStyles()

    cardForm.setOnCardFormValidListener { valid: Boolean ->
      Log.v(
        "MainActivity",
        "CardForm valid: $valid"
      )
    }

    refundButton.setOnClickListener { _ ->
      // Code here executes on main thread after user presses button
      cardForm.clearFocus()
      infoView.text = ""
      progressBar.visibility = View.VISIBLE
      val refundRequest = RefundRequest(100)
      cardForm.refund(refundRequest, paymentCallback)
    }

    tokenizeButton.setOnClickListener { _ ->
      // Code here executes on main thread after user presses button
      cardForm.clearFocus()
      infoView.text = ""
      progressBar.visibility = View.VISIBLE
      cardForm.tokenize(tokenizeCallback)
    }

    payButton.setOnClickListener { _ ->
      // Code here executes on main thread after user presses button
      cardForm.clearFocus()
      infoView.text = ""
      progressBar.visibility = View.VISIBLE
      val chargeRequest = ChargeRequest(100, true)
      cardForm.charge(chargeRequest, paymentCallback)
    }
  }

  private fun initStyles() {
    try {
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

      //Set up client configuration
      val clientConfiguration = ClientConfiguration.fromKeys(
        "staging",
        "pk_XXXXXXXXXXXXXXXXXXXXXXXXXX",
        "MRT_XXXXXXXXXXXXXXXXXXXXXXXXXX"
      )

      //Set up field options
      val fieldOptions = FieldOptions(
        FieldConfiguration(DisplayConfiguration.OPTIONAL),
        FieldConfiguration(DisplayConfiguration.REQUIRED)
      )

      //Init card form with you custom configurations
      cardForm.init(clientConfiguration, fieldOptions)
    } catch (e: InvalidArgumentException) {
      infoView.text = e.message
    }
  }
}