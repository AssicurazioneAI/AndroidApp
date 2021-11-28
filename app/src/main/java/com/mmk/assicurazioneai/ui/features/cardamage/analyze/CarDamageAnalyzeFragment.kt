package com.mmk.assicurazioneai.ui.features.cardamage.analyze

import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mmk.assicurazioneai.R
import com.mmk.assicurazioneai.databinding.FragmentAnalyzeDamageBinding
import com.mmk.assicurazioneai.ui.base.BaseFragment
import com.mmk.assicurazioneai.ui.base.BaseViewModel
import com.mmk.assicurazioneai.ui.features.cardamage.damageresult.DamageResultDialogFragment
import com.mmk.assicurazioneai.utils.binding.viewBinding
import com.mmk.assicurazioneai.utils.observeSingleEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.google.android.material.shape.MaterialShapeDrawable

import com.google.android.material.shape.ShapeAppearanceModel
import timber.log.Timber


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
        changeBottomSheetState(binding.bottomSheet.root, BottomSheetBehavior.STATE_COLLAPSED)
        lifecycleScope.launch {
            delay(200)
            changeBottomSheetState(
                binding.bottomSheet.root,
                BottomSheetBehavior.STATE_EXPANDED
            )
        }
    }

    override fun observeValues() {
        super.observeValues()
        viewModel.imagePath.observe(viewLifecycleOwner) {
            binding.imageView.setImageURI(Uri.parse(it))
        }

        viewModel.onImageSent.observeSingleEvent(viewLifecycleOwner) {
            val imageRectF = getImageBounds(binding.imageView)

            Timber.e(imageRectF.toString())

            val coordinates = listOf(
                RectF(
                    imageRectF.centerX() - 100,
                    imageRectF.centerY() - 100,
                    imageRectF.centerX() + 100,
                    imageRectF.centerY() + 100
                )
            )
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
            changeBottomSheetState(binding.bottomSheet.root, BottomSheetBehavior.STATE_COLLAPSED)

        }
        binding.bottomSheet.retakeButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun drawDamageRectangle(coordinates: List<RectF>) {
        binding.rectangleView.drawRectBounds(coordinates)
    }

    private fun changeBottomSheetState(view: View, state: Int) {
        val bottomSheetBehavior = BottomSheetBehavior.from(view)
        bottomSheetBehavior.isDraggable = false
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    ViewCompat.setBackground(view, createMaterialShapeDrawable(view))
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })
        bottomSheetBehavior.state = state

    }


    private fun createMaterialShapeDrawable(bottomSheet: View): MaterialShapeDrawable {
        val shapeAppearanceModel =
            //Create a ShapeAppearanceModel with the same shapeAppearanceOverlay used in the style
            ShapeAppearanceModel.builder(context, 0, R.style.CustomShapeAppearanceBottomSheetDialog)
                .build()

        //Create a new MaterialShapeDrawable (you can't use the original MaterialShapeDrawable in the BottomSheet)
        val currentMaterialShapeDrawable = bottomSheet.background as MaterialShapeDrawable
        val newMaterialShapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
        //Copy the attributes in the new MaterialShapeDrawable
        newMaterialShapeDrawable.initializeElevationOverlay(context)
        newMaterialShapeDrawable.fillColor = currentMaterialShapeDrawable.fillColor
        newMaterialShapeDrawable.tintList = currentMaterialShapeDrawable.tintList
        newMaterialShapeDrawable.elevation = currentMaterialShapeDrawable.elevation
        newMaterialShapeDrawable.strokeWidth = currentMaterialShapeDrawable.strokeWidth
        newMaterialShapeDrawable.strokeColor = currentMaterialShapeDrawable.strokeColor
        newMaterialShapeDrawable.strokeColor = currentMaterialShapeDrawable.strokeColor
        return newMaterialShapeDrawable
    }

    private fun getImageBounds(imageView: ImageView): RectF {
        val bounds = RectF()
        if (imageView.drawable != null) {
            imageView.imageMatrix.mapRect(bounds, RectF(imageView.drawable.bounds))
        }
        return bounds
    }


}