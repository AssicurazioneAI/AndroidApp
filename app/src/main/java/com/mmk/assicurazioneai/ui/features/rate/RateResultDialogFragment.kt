package com.mmk.assicurazioneai.ui.features.rate

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.mmk.assicurazioneai.R
import com.mmk.assicurazioneai.databinding.FragmentCameraBinding
import com.mmk.assicurazioneai.databinding.FragmentRateResultBinding
import com.mmk.assicurazioneai.ui.base.BaseDialogFragment
import com.mmk.assicurazioneai.utils.binding.viewBinding

class RateResultDialogFragment:BaseDialogFragment(R.layout.fragment_rate_result) {

    override val binding: FragmentRateResultBinding by viewBinding(FragmentRateResultBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.WideDialog)
    }

    override fun initView() {
        super.initView()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}