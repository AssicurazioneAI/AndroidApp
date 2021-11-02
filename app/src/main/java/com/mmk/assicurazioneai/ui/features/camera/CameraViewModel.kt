package com.mmk.assicurazioneai.ui.features.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mmk.assicurazioneai.R
import com.mmk.assicurazioneai.ui.base.BaseViewModel
import com.mmk.assicurazioneai.ui.base.ErrorMessage
import com.mmk.assicurazioneai.ui.base.UiState
import com.mmk.assicurazioneai.utils.SingleEvent
import com.mmk.domain.model.error.ErrorEntity
import com.mmk.domain.model.onError
import com.mmk.domain.model.onSuccess
import com.mmk.domain.usecase.image.SendingImageUseCase

class CameraViewModel(private val sendingImageUseCase: SendingImageUseCase) : BaseViewModel() {

    private var _sendingImageUiState: MutableLiveData<UiState> = MutableLiveData()
    val sendingImageUiState: LiveData<UiState> = _sendingImageUiState

    private var _onImageSent: MutableLiveData<SingleEvent<Unit>> = MutableLiveData()
    val onImageSent: LiveData<SingleEvent<Unit>> = _onImageSent


    fun sendImage(imagePath: String?) = executeUseCase(_sendingImageUiState) {
        val response = sendingImageUseCase(imagePath)
        response.onSuccess {
            _onImageSent.value = SingleEvent(Unit)
        }.onError {
            when (it) {
                ErrorEntity.ImageError.WrongFormat -> setErrorMessage(ErrorMessage.ResourceId(R.string.error_image_wrong_format))
                else -> handleOtherErrorCases(it)
            }
        }
    }
}