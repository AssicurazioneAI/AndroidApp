package com.mmk.assicurazioneai.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.mmk.assicurazioneai.utils.extensions.showSnackbar
import com.mmk.assicurazioneai.utils.observeSingleEvent

abstract class BaseFragment(@LayoutRes private val contentLayoutId: Int = 0) :
    Fragment(contentLayoutId) {

    abstract val binding: ViewBinding
    protected open val viewModel: BaseViewModel? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setClicks()
        observeValues()
    }

    protected open fun initView() = Unit
    protected open fun setClicks() = Unit
    protected open fun observeValues() {
        viewModel?.errorMessage?.observeSingleEvent(viewLifecycleOwner) {
            when (it) {
                is ErrorMessage.Message -> showSnackbar(it.message)
                is ErrorMessage.ResourceId -> showSnackbar(it.id)
            }
        }
    }
}