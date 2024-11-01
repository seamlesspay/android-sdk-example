/**
 * Copyright (c) Seamless Payments, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.seamlesspay.demo;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.material.tabs.TabLayout;
import com.seamlesspay.api.error.ApiError;
import com.seamlesspay.api.exceptions.InvalidArgumentException;
import com.seamlesspay.api.models.ClientConfiguration;
import com.seamlesspay.example.R;
import com.seamlesspay.ui.common.CardFormValidListener;
import com.seamlesspay.ui.common.PaymentCallback;
import com.seamlesspay.ui.common.TokenizeCallback;
import com.seamlesspay.ui.models.ChargeRequest;
import com.seamlesspay.ui.models.DisplayConfiguration;
import com.seamlesspay.ui.models.FieldConfiguration;
import com.seamlesspay.ui.models.FieldOptions;
import com.seamlesspay.ui.models.PaymentResponse;
import com.seamlesspay.ui.models.RefundRequest;
import com.seamlesspay.ui.models.TokenizeResponse;
import com.seamlesspay.ui.models.style.ColorPalette;
import com.seamlesspay.ui.models.style.Colors;
import com.seamlesspay.ui.models.style.ElevationLevel;
import com.seamlesspay.ui.models.style.IconSet;
import com.seamlesspay.ui.models.style.Shadow;
import com.seamlesspay.ui.models.style.Shapes;
import com.seamlesspay.ui.models.style.StyleOptions;
import com.seamlesspay.ui.models.style.ThemeColors;
import com.seamlesspay.ui.models.style.Typography;
import com.seamlesspay.ui.paymentinputs.direct.MultiLineCardForm;
import com.seamlesspay.ui.paymentinputs.direct.SingleLineCardForm;

public class MainActivity extends AppCompatActivity {
	SingleLineCardForm mSingleLineCardForm;
	MultiLineCardForm mMultiLineCardForm;
	TextView mInfoView;
	ProgressBar mProgressBar;
	TabLayout mTabLayout;
	boolean isMultiLine = false;

	private final TokenizeCallback tokenizeCallback = new TokenizeCallback() {
		@Override
		public void success(@NonNull TokenizeResponse tokenResponse) {
			mInfoView.setText(tokenResponse.toString());
			mProgressBar.setVisibility(View.GONE);
		}

		@SuppressLint("SetTextI18n")
		@Override
		public void failure(@Nullable ApiError apiError) {
			if (apiError != null) {
				mInfoView.setText("Error\n" + apiError.getStatusDescription());
			}
			mProgressBar.setVisibility(View.GONE);
		}
	};

	private final PaymentCallback paymentCallback = new PaymentCallback() {
		@Override
		public void success(@NonNull PaymentResponse paymentResponse) {
			mInfoView.setText(paymentResponse.toString());
			mProgressBar.setVisibility(View.GONE);
		}

		@SuppressLint("SetTextI18n")
		@Override
		public void failure(@Nullable ApiError apiError) {
			if (apiError != null) {
				mInfoView.setText("Error\n" + apiError.getStatusDescription());
			}
			mProgressBar.setVisibility(View.GONE);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		Button payButton = findViewById(R.id.payButton);
		Button tokenizeButton = findViewById(R.id.tokenizeButton);
		Button refundButton = findViewById(R.id.refundButton);
		mProgressBar = findViewById(R.id.progress);
		mTabLayout = findViewById(R.id.tabLayout);
		mInfoView = findViewById(R.id.infoView);
		mSingleLineCardForm = findViewById(R.id.cardSingleLine);
		mMultiLineCardForm = findViewById(R.id.cardMultiLine);
		try {
			StyleOptions styleOptions = new StyleOptions(
					new Colors(
							new ColorPalette(new ThemeColors(Color.RED, Color.BLUE, Color.MAGENTA)),
							new ColorPalette(new ThemeColors(Color.RED, Color.BLUE, Color.MAGENTA))
					),
					new Shapes(50F, new Shadow(ElevationLevel.Level1)),
					new Typography(com.seamlesspay.R.font.roboto_regular, 1f),
					IconSet.DARK
			);
			ClientConfiguration clientConfiguration =
					ClientConfiguration.fromKeys("staging", "pk_XXXXXXXXXXXXXXXXXXXXXXXXXX", "MRT_XXXXXXXXXXXXXXXXXXXXXXXXXX");
			FieldOptions option = new FieldOptions(new FieldConfiguration(DisplayConfiguration.OPTIONAL),
					new FieldConfiguration(DisplayConfiguration.REQUIRED));
			mSingleLineCardForm.init(clientConfiguration, option);
			mMultiLineCardForm.init(clientConfiguration, null, null);
		} catch (InvalidArgumentException e) {
			mInfoView.setText(e.getMessage());
		}

		mSingleLineCardForm.setOnCardFormValidListener(
				valid -> Log.v("MainActivity", "SingleLine valid: " + valid));

		setUpTabLayout();

		refundButton.setOnClickListener(v -> {
			// Code here executes on main thread after user presses button
			mSingleLineCardForm.clearFocus();
			mMultiLineCardForm.clearFocus();
			mInfoView.setText("");
			mProgressBar.setVisibility(View.VISIBLE);

			RefundRequest refundRequest = new RefundRequest(100);
			if (isMultiLine) {
				mMultiLineCardForm.refund(refundRequest, paymentCallback);
			} else {
				mSingleLineCardForm.refund(refundRequest, paymentCallback);
			}
		});

		tokenizeButton.setOnClickListener(v -> {
			// Code here executes on main thread after user presses button
			mSingleLineCardForm.clearFocus();
			mMultiLineCardForm.clearFocus();
			mInfoView.setText("");
			mProgressBar.setVisibility(View.VISIBLE);

			if (isMultiLine) {
				mMultiLineCardForm.tokenize(tokenizeCallback);
			} else {
				mSingleLineCardForm.tokenize(tokenizeCallback);
			}
		});

		payButton.setOnClickListener(v -> {
			// Code here executes on main thread after user presses button
			mSingleLineCardForm.clearFocus();
			mMultiLineCardForm.clearFocus();
			mInfoView.setText("");
			mProgressBar.setVisibility(View.VISIBLE);

			ChargeRequest chargeRequest = new ChargeRequest(100, true);
			if (isMultiLine) {
				mMultiLineCardForm.charge(chargeRequest, paymentCallback);
			} else {
				mSingleLineCardForm.charge(chargeRequest, paymentCallback);
			}
		});
	}

	private void setUpTabLayout() {
		mTabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				if (tab.getPosition() == 1) {
					isMultiLine = true;
					mSingleLineCardForm.setVisibility(View.GONE);
					mMultiLineCardForm.setVisibility(View.VISIBLE);
				} else {
					isMultiLine = false;
					mSingleLineCardForm.setVisibility(View.VISIBLE);
					mMultiLineCardForm.setVisibility(View.GONE);
				}
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});
	}
}
