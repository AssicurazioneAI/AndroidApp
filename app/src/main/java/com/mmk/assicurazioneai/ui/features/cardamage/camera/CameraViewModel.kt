package com.mmk.assicurazioneai.ui.features.cardamage.camera

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

class CameraViewModel : BaseViewModel() {
    val isFlashOn = MutableLiveData(false)

    fun changeFlashMode() {
        isFlashOn.value = isFlashOn.value?.not()
    }
}