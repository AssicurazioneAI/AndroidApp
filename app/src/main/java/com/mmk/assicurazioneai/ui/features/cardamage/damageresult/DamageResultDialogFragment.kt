package com.mmk.assicurazioneai.ui.features.cardamage.damageresult

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.mmk.assicurazioneai.R
import com.mmk.assicurazioneai.databinding.FragmentDamageResultBinding
import com.mmk.assicurazioneai.ui.base.BaseDialogFragment
import com.mmk.assicurazioneai.utils.binding.viewBinding
import com.mmk.domain.model.CarDamage

class DamageResultDialogFragment : BaseDialogFragment(R.layout.fragment_damage_result) {

    override val binding: FragmentDamageResultBinding by viewBinding(FragmentDamageResultBinding::inflate)

    companion object {
        const val ARGS_KEY_CAR_DAMAGE = "carDamage"
    }

    private lateinit var carDamage: CarDamage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.WideDialog)
        carDamage = arguments?.getSerializable(ARGS_KEY_CAR_DAMAGE) as CarDamage
    }

    override fun initView() {
        super.initView()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val damageTypeCount = getDamageTypeCount()
        val max = damageTypeCount.maxByOrNull { it.value }
        val worstDamageTitle = max?.key ?: ""
        binding.titleTv.text = worstDamageTitle
        binding.descriptionTv.text = getString(
            R.string.text_damage_severity,
            damageTypeCount[CarDamage.DamagedPart.SEVERITY_LIGHT],
            damageTypeCount[CarDamage.DamagedPart.SEVERITY_MEDIUM],
            damageTypeCount[CarDamage.DamagedPart.SEVERITY_HARD]
        )
    }

    private fun getDamageTypeCount(): Map<String, Int> {
        val damageSeverityWithCounts = hashMapOf(
            CarDamage.DamagedPart.SEVERITY_LIGHT to 0,
            CarDamage.DamagedPart.SEVERITY_MEDIUM to 0,
            CarDamage.DamagedPart.SEVERITY_HARD to 0
        )
        carDamage.damagedPartList.forEach {
            val currentCount = damageSeverityWithCounts.getOrPut(it.severity) { 0 }
            damageSeverityWithCounts[it.severity] = currentCount + 1
        }
        return damageSeverityWithCounts

    }

    override fun setClicks() {
        super.setClicks()
        binding.cancelRateButton.setOnClickListener { dismiss() }
        binding.submitRateButton.setOnClickListener {
            findNavController().navigate(R.id.action_damageResultDialogFragment_to_rateResultDialogFragment)
        }

    }

}