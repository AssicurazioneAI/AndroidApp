package com.mmk.assicurazioneai.ui.base

import androidx.annotation.StringRes

sealed class UiState {
    object Success : UiState()
    object Loading : UiState()
    object Empty : UiState()
    data class Error(val errorState: ErrorState? = null) : UiState()
}

sealed class ErrorState() {
    data class ResourceId(@StringRes val id: Int) : ErrorState()
    data class Message(val message: String?) : ErrorState()
}