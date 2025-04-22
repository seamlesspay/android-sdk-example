package com.seamlesspay.demo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionInfo(
  val amount: Int,
  val transactionType: TransactionType
) : Parcelable