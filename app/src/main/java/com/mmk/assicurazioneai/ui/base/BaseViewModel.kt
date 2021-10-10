package com.mmk.assicurazioneai.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmk.assicurazioneai.utils.SingleEvent
import com.mmk.domain.model.Result
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {


    //This message object can be used to show error message in UI
    private val _message = MutableLiveData<SingleEvent<ErrorState>>()
    val message: LiveData<SingleEvent<ErrorState>> = _message

    protected fun <T : Any> executeUseCase(
        uiState: MutableLiveData<UiState> = MutableLiveData(),
        action: suspend () -> Result<T>

    ) {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            action()
        }
    }

    protected fun showErrorMessage(errorState: ErrorState) {
        _message.value = SingleEvent(errorState)
    }


}