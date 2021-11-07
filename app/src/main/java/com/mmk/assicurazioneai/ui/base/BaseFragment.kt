package com.mmk.assicurazioneai.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment(@LayoutRes private val contentLayoutId: Int = 0) :
    Fragment(contentLayoutId) {

    abstract val binding: ViewBinding

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
    protected open fun initView()= Unit
    protected open fun setClicks()= Unit
    protected open fun observeValues()= Unit
}