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
            when (uiState) {
                UiState.Loading -> {
                    animate().scaleX(1f).scaleY(1f).withStartAction {
                        isVisible = true
                    }
                }
                else -> {
                    animate().scaleX(0f).scaleY(0f).withEndAction {
                        isVisible = false
                    }

                }
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