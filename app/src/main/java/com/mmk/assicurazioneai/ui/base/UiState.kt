package com.mmk.assicurazioneai.ui.base

import androidx.annotation.StringRes

sealed class UiState {
    object Success : UiState()
    object Loading : UiState()
    object Empty : UiState()
    data class Error(val errorMessage: ErrorMessage? = null) : UiState()
}

sealed class ErrorMessage() {
    data class ResourceId(@StringRes val id: Int) : ErrorMessage()
    data class Message(val message: String?) : ErrorMessage()
}