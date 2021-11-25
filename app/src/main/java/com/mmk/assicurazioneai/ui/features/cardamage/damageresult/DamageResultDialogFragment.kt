package com.mmk.assicurazioneai.ui.features.cardamage.damageresult

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.mmk.assicurazioneai.R
import com.mmk.assicurazioneai.databinding.FragmentDamageResultBinding
import com.mmk.assicurazioneai.databinding.FragmentRateResultBinding
import com.mmk.assicurazioneai.ui.base.BaseDialogFragment
import com.mmk.assicurazioneai.utils.binding.viewBinding
import com.mmk.assicurazioneai.utils.extensions.toast

class DamageResultDialogFragment : BaseDialogFragment(R.layout.fragment_damage_result) {

    override val binding: FragmentDamageResultBinding by viewBinding(FragmentDamageResultBinding::inflate)

    companion object {
        const val ARGS_KEY_DAMAGE_TYPE = "damageResultType"
    }

    private lateinit var damageType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.WideDialog)
        damageType = arguments?.getString(ARGS_KEY_DAMAGE_TYPE, "") ?: ""
    }

    override fun initView() {
        super.initView()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.titleTv.text = damageType
    }

    override fun setClicks() {
        super.setClicks()
        binding.cancelRateButton.setOnClickListener { dismiss() }
        binding.submitRateButton.setOnClickListener {
            findNavController().navigate(R.id.action_damageResultDialogFragment_to_rateResultDialogFragment)
        }

    }

}