package com.mmk.assicurazioneai.ui.features.auth.otpcode

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mmk.assicurazioneai.ui.base.BaseViewModel
import com.mmk.assicurazioneai.ui.base.UiState
import com.mmk.assicurazioneai.utils.SingleEvent
import com.mmk.domain.model.Result
import com.mmk.domain.model.onSuccess
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class OtpCodeViewModel : BaseViewModel() {

    val pinCode = MutableLiveData<String?>()
    private var timer: CountDownTimer? = null

    private val _onPinCodeTimeOut = MutableLiveData<Boolean>(false)
    val onPinCodeTimeOut: LiveData<Boolean> = _onPinCodeTimeOut

    private val _onCountDownTimer = MutableLiveData<String>()
    val onCountDownTimer: LiveData<String> = _onCountDownTimer

    private var _verifyOtpCodeUiState: MutableLiveData<UiState> = MutableLiveData()
    val verifyOtpCodeUiState: LiveData<UiState> = _verifyOtpCodeUiState

    private val _onCorrectPinCode = MutableLiveData<SingleEvent<Unit>>()
    val onCorrectPinCode: LiveData<SingleEvent<Unit>> = _onCorrectPinCode

    init {
        startTimer()
    }

    fun onClickVerifyButton() = executeUseCase(uiState = _verifyOtpCodeUiState) {
        withContext(IO) { delay(1000L) }
        val response = Result.Success(Unit)
        response.onSuccess {
            _onCorrectPinCode.value = SingleEvent(Unit)
        }
    }

    fun resendVerificationCode() {
        startTimer()
    }

    private fun startTimer() {
        _onPinCodeTimeOut.value = false
        val VERIFICATION_CODE_TIMEOUT = 30 * 1000L
        timer = object : CountDownTimer(VERIFICATION_CODE_TIMEOUT, 1000L) {
            override fun onFinish() {
                _onPinCodeTimeOut.value = true
            }

            override fun onTick(p0: Long) {
                val minutes=p0/60000
                val seconds=p0%60000/1000
                _onCountDownTimer.value ="$minutes:$seconds"
            }
        }
        timer?.start()
    }


    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}