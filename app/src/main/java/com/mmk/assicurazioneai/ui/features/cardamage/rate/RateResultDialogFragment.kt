package com.mmk.assicurazioneai.ui.features.cardamage.rate

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.mmk.assicurazioneai.R
import com.mmk.assicurazioneai.databinding.FragmentRateResultBinding
import com.mmk.assicurazioneai.ui.base.BaseDialogFragment
import com.mmk.assicurazioneai.utils.binding.viewBinding
import com.mmk.assicurazioneai.utils.extensions.toast

class RateResultDialogFragment : BaseDialogFragment(R.layout.fragment_rate_result) {

    override val binding: FragmentRateResultBinding by viewBinding(FragmentRateResultBinding::inflate)

    private val rateViews by lazy {
        listOf(
            binding.rateButton1,
            binding.rateButton2,
            binding.rateButton3,
            binding.rateButton4,
            binding.rateButton5
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.WideDialog)
    }

    override fun initView() {
        super.initView()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun setClicks() {
        super.setClicks()
        binding.cancelRateButton.setOnClickListener { dismiss() }
        binding.submitRateButton.setOnClickListener {
            context.toast(R.string.rate_message)
            dismiss()
        }
        binding.rateButton1.setOnClickListener { onUserSelectRate(1) }
        binding.rateButton2.setOnClickListener { onUserSelectRate(2) }
        binding.rateButton3.setOnClickListener { onUserSelectRate(3) }
        binding.rateButton4.setOnClickListener { onUserSelectRate(4) }
        binding.rateButton5.setOnClickListener { onUserSelectRate(5) }
    }

    private fun onUserSelectRate(rate: Int) {
        rateViews.forEach {
            val tag = it.tag as String
            if (tag.toInt() == rate)
                it.animate().scaleX(1.6f).scaleY(1.6f).duration = 150L
            else
                it.animate().scaleX(1f).scaleY(1f).duration = 150L
        }
    }
}