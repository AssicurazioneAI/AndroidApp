package com.mmk.assicurazioneai.ui.features.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mmk.assicurazioneai.ui.base.UiState
import com.mmk.domain.usecase.image.SendingImageUseCase

class CameraViewModel(private val sendingImageUseCase: SendingImageUseCase) {

    private var _sendingImageUiState: MutableLiveData<UiState> = MutableLiveData()
    val sendingImageUiState: LiveData<UiState> = _sendingImageUiState

    fun sendImage(imagePath: String?) {
        _sendingImageUiState.value = UiState.Loading
    }
}