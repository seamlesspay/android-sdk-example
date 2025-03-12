package com.seamlesspay.demo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

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

}