package com.mmk.assicurazioneai.ui.features.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmk.assicurazioneai.R
import com.mmk.assicurazioneai.ui.base.BaseViewModel
import com.mmk.assicurazioneai.ui.base.ErrorState
import com.mmk.assicurazioneai.ui.base.UiState
import com.mmk.assicurazioneai.utils.SingleEvent
import com.mmk.domain.model.error.ErrorEntity
import com.mmk.domain.model.onError
import com.mmk.domain.model.onSuccess
import com.mmk.domain.usecase.image.SendingImageUseCase
import kotlinx.coroutines.launch

class CameraViewModel(private val sendingImageUseCase: SendingImageUseCase) : BaseViewModel() {

    private var _sendingImageUiState: MutableLiveData<UiState> = MutableLiveData()
    val sendingImageUiState: LiveData<UiState> = _sendingImageUiState

    private var _onImageSent: MutableLiveData<SingleEvent<Unit>> = MutableLiveData()
    val onImageSent: LiveData<SingleEvent<Unit>> = _onImageSent

    private var _errorState: MutableLiveData<SingleEvent<ErrorState>> = MutableLiveData()
    val errorState: LiveData<SingleEvent<ErrorState>> = _errorState

    fun sendImage(imagePath: String?) = executeUseCase(_sendingImageUiState) {
        val response = sendingImageUseCase(imagePath)
        response.onSuccess {
            _sendingImageUiState.value = UiState.Success
            _onImageSent.value = SingleEvent(Unit)
        }.onError {
            _sendingImageUiState.value = UiState.Error()
            _errorState.value = when (it) {
                is ErrorEntity.ApiError.Other -> SingleEvent(ErrorState.Message(it.errorMessage))
                ErrorEntity.ApiError.ServerProblem -> SingleEvent((ErrorState.ResourceId(R.string.error_server_problem)))
                ErrorEntity.CommonError.EmptyOrNullData -> SingleEvent((ErrorState.ResourceId(R.string.error_empty_data)))
                ErrorEntity.CommonError.NoInternetConnection -> SingleEvent(
                    (ErrorState.ResourceId(
                        R.string.error_no_internet_connection
                    ))
                )
                ErrorEntity.CommonError.Unknown -> SingleEvent((ErrorState.ResourceId(R.string.error_unknown_error_occured)))
                ErrorEntity.ImageError.WrongFormat -> SingleEvent((ErrorState.ResourceId(R.string.error_image_wrong_format)))
                else -> SingleEvent((ErrorState.ResourceId(R.string.error_unknown_error_occured)))
            }
        }
    }
}