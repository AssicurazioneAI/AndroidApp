package com.mmk.assicurazioneai.ui.features.cardamage.analyze

import android.graphics.RectF
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mmk.assicurazioneai.R
import com.mmk.assicurazioneai.databinding.FragmentAnalyzeDamageBinding
import com.mmk.assicurazioneai.databinding.FragmentCameraBinding
import com.mmk.assicurazioneai.ui.base.BaseFragment
import com.mmk.assicurazioneai.ui.base.BaseViewModel
import com.mmk.assicurazioneai.ui.features.cardamage.damageresult.DamageResultDialogFragment
import com.mmk.assicurazioneai.utils.binding.viewBinding
import com.mmk.assicurazioneai.utils.observeSingleEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CarDamageAnalyzeFragment : BaseFragment(R.layout.fragment_analyze_damage) {
    override val binding: FragmentAnalyzeDamageBinding by viewBinding(FragmentAnalyzeDamageBinding::inflate)
    override val viewModel: CarDamageAnalyzeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setImagePath(arguments?.getString("imageUri", ""))
    }


    override fun initView() {
        super.initView()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
        changeBottomSheetState(binding.bottomSheet.root,BottomSheetBehavior.STATE_COLLAPSED)
        lifecycleScope.launch {
            delay(200)
            changeBottomSheetState(binding.bottomSheet.root,BottomSheetBehavior.STATE_HALF_EXPANDED)
        }
    }

    override fun observeValues() {
        super.observeValues()
        viewModel.imagePath.observe(viewLifecycleOwner) {
            binding.imageView.setImageURI(Uri.parse(it))
        }

        viewModel.onImageSent.observeSingleEvent(viewLifecycleOwner) {
            val coordinates = listOf(RectF(100f, 200f, 500f, 600f))
            drawDamageRectangle(coordinates)
            lifecycleScope.launch {
                val damageType = "HARD DAMAGE"
                delay(1000L)
                findNavController().navigate(
                    R.id.action_carDamageAnalyzeFragment_to_damageResultDialogFragment,
                    bundleOf(DamageResultDialogFragment.ARGS_KEY_DAMAGE_TYPE to damageType)
                )
            }
        }
    }

    override fun setClicks() {
        super.setClicks()
        binding.bottomSheet.sendButton.setOnClickListener {
            viewModel.sendImage()
            changeBottomSheetState(binding.bottomSheet.root,BottomSheetBehavior.STATE_COLLAPSED)

        }
        binding.bottomSheet.retakeButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun drawDamageRectangle(coordinates: List<RectF>) {
        binding.rectangleView.drawRectBounds(coordinates)
    }

    private fun changeBottomSheetState(view: View, state: Int) {
        BottomSheetBehavior.from(view).state = state

    }


}