package com.mmk.assicurazioneai.utils.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.mmk.assicurazioneai.R
import com.mmk.assicurazioneai.databinding.ViewgroupProgressBinding

class ProgressView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private val binding by lazy {
        ViewgroupProgressBinding.inflate(LayoutInflater.from(context), this)
    }

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ProgressView)
        binding.titleTv.text = attributes.getString(R.styleable.ProgressView_title) ?: ""
        binding.descriptionTv.text =
            attributes.getString(R.styleable.ProgressView_description) ?: ""

        attributes.recycle()
    }

}