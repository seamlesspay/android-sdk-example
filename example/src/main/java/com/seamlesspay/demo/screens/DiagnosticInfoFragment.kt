package com.seamlesspay.demo.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.seamlesspay.demo.base.BaseFragment
import com.seamlesspay.demo.util.buildDebugInfo
import com.seamlesspay.demo.util.copyToClipboard
import com.seamlesspay.example.R
import com.seamlesspay.example.databinding.FragmentDiagnosticInfoBinding
import com.seamlesspay.example.databinding.ItemDebigInfoBinding

class DiagnosticInfoFragment : BaseFragment<FragmentDiagnosticInfoBinding>() {
    private val debugInfo by lazy {
        buildDebugInfo(requireContext())
    }
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDiagnosticInfoBinding
        get() = FragmentDiagnosticInfoBinding::inflate

    override fun FragmentDiagnosticInfoBinding.initViews() {
        // Set Up Toolbar

        topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        // Set Up Controls

        btnCopy.setOnClickListener {
            requireContext().copyToClipboard(
                label = getString(R.string.response_code_title),
                text = debugInfo.joinToString("\n") { (key, value) -> "$key: $value" }
            )
        }

        // Populate list

        debugInfo.forEach { pair ->
            val item = ItemDebigInfoBinding.inflate(layoutInflater)
            item.tvTitle.text = pair.first
            item.tvValue.text = pair.second
            llInfoContainer.addView(item.root)
        }
    }
}