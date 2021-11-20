package com.mmk.assicurazioneai.ui.features.camera

import android.net.Uri
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
import com.mmk.domain.usecase.cardamage.GettingCarDamageUseCase
import kotlinx.coroutines.delay

class CameraViewModel(private val gettingCarDamageUseCase: GettingCarDamageUseCase) : BaseViewModel() {

    private var _sendingImageUiState: MutableLiveData<UiState> = MutableLiveData()
    val sendingImageUiState: LiveData<UiState> = _sendingImageUiState

    private val _onImageSent: MutableLiveData<SingleEvent<Unit>> = MutableLiveData()
    val onImageSent: LiveData<SingleEvent<Unit>> = _onImageSent

    private val _imagePath = MutableLiveData<String?>()
    val imagePath: LiveData<String?> = _imagePath

    val isFlashOn = MutableLiveData<Boolean>(false)

    fun changeFlashMode() {
        isFlashOn.value = isFlashOn.value?.not()
    }

    fun sendImage() = executeUseCase(_sendingImageUiState) {
        val imagePath: String? = _imagePath.value
        val response = gettingCarDamageUseCase(imagePath)
        response.onSuccess {
            _onImageSent.value = SingleEvent(Unit)
        }.onError {
            when (it) {
                ErrorEntity.ImageError.WrongFormat -> setErrorMessage(ErrorMessage.ResourceId(R.string.error_image_wrong_format))
                else -> handleOtherErrorCases(it)
            }
        }
    }

    fun setImagePath(imageUri: Uri?) {
        imageUri?.let {
            _imagePath.value = it.toString()
            sendImage()
        }
    }

    fun resetImagePath() {
        _imagePath.value = null
    }
}