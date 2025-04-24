package com.seamlesspay.demo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultInfo(
  val resultType: ResultType,
  val result: String
) : Parcelable