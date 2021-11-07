package com.mmk.assicurazioneai.utils.binding

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.mmk.assicurazioneai.ui.base.UiState

object BindingAdapters {

    @BindingAdapter("visibility")
    @JvmStatic
    fun View.setVisibility(isVisible: Boolean) {
        this.visibility = if (isVisible) View.VISIBLE else View.GONE
    }


    @BindingAdapter("showOnLoading")
    @JvmStatic
    fun View.showOnLoading(uiState: UiState?) {
        this.apply {
            isVisible = when (uiState) {
                UiState.Loading -> true
                else -> false
            }
        }
    }


    @BindingAdapter("hideOnLoading")
    @JvmStatic
    fun View.showOnNoLoading(uiState: UiState?) {
        this.apply {
            isVisible = when (uiState) {
                UiState.Loading -> false
                else -> true

            }
        }
    }

    @BindingAdapter("showOnSuccess")
    @JvmStatic
    fun View.showOnSuccess(uiState: UiState?) {
        this.apply {
            isVisible = when (uiState) {
                UiState.Success -> true
                else -> false

            }
        }
    }


    @BindingAdapter("showOnEmptyData")
    @JvmStatic
    fun View.showOnEmptyData(uiState: UiState?) {
        this.apply {
            isVisible = when (uiState) {
                UiState.Empty -> true
                else -> false
            }
        }
    }
}