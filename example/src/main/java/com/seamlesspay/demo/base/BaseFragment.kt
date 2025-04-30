package com.seamlesspay.demo.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.seamlesspay.example.R

abstract class BaseFragment<B : ViewBinding> : Fragment() {

  private var _binding: B? = null
  protected val binding
    get() = _binding ?: throw RuntimeException("BaseBindingFragment: _binding is null")

  abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> B

  /*
  * Method can be used to set up binding during view creation process
   */
  abstract fun B.initViews()

  /*
  * Method can be used to clear views before destroy binding
   */
  open fun B.destroyViews() {
    //Optional for overriding
  }

  @CallSuper
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = bindingInflater.invoke(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.initViews()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    binding.destroyViews()
    _binding = null
  }

  private var progressDialog: Dialog? = null

  protected fun showFullScreenProgress() {
    if (progressDialog?.isShowing == true) return

    progressDialog = Dialog(requireContext()).apply {
      requestWindowFeature(Window.FEATURE_NO_TITLE)
      setContentView(R.layout.layout_progress)
      setCancelable(false)

      window?.let { win ->
        win.setLayout(
          ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.MATCH_PARENT
        )

        win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        win.setBackgroundDrawable(
          ColorDrawable(
            Color.TRANSPARENT
          )
        )
      }
      show()
    }
  }

  protected fun hideFullScreenProgress() {
    progressDialog?.dismiss()
    progressDialog = null
  }

  protected fun showSnackbar(parentView : View, @StringRes message: Int, @StringRes actionText: Int = R.string.title_menu_close) {
    val snackbar = Snackbar.make(parentView, getString(message), Snackbar.LENGTH_LONG)
    snackbar.setAction(actionText) {
      snackbar.dismiss()
    }
    snackbar.show()
  }
}