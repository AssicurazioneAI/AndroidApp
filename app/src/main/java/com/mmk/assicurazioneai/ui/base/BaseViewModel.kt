package com.mmk.assicurazioneai.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmk.assicurazioneai.R
import com.mmk.assicurazioneai.utils.SingleEvent
import com.mmk.domain.model.Result
import com.mmk.domain.model.error.ErrorEntity
import com.mmk.domain.model.onError
import com.mmk.domain.model.onSuccess
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {


    //This message object can be used to show error message in UI, without changing uiState
    private var _errorMessage: MutableLiveData<SingleEvent<ErrorMessage>> = MutableLiveData()
    val errorMessage: LiveData<SingleEvent<ErrorMessage>> = _errorMessage

    private var _errorEntity: MutableLiveData<SingleEvent<ErrorEntity>> = MutableLiveData()
    val errorEntity: LiveData<SingleEvent<ErrorEntity>> = _errorEntity

    protected fun <T : Any> executeUseCase(
        uiState: MutableLiveData<UiState> = MutableLiveData(),
        action: suspend () -> Result<T>

    ) {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            action().also {
                it.onSuccess {
                    uiState.value = UiState.Success
                }.onError { error ->
                    uiState.value = UiState.Error()
                    setErrorEntity(error)
                }
            }
        }
    }

    protected fun setErrorMessage(errorMessage: ErrorMessage) {
        _errorMessage.value = SingleEvent(errorMessage)
    }

    private fun setErrorEntity(errorEntity: ErrorEntity?) {
        errorEntity?.let {
            _errorEntity.value = SingleEvent(errorEntity)
        }
    }

    protected fun handleOtherErrorCases(errorEntity: ErrorEntity?) {
        errorEntity?.let {
            when (it) {
                is ErrorEntity.ApiError.Other -> setErrorMessage(ErrorMessage.Message(it.errorMessage))
                ErrorEntity.ApiError.ServerProblem -> setErrorMessage(ErrorMessage.ResourceId(R.string.error_server_problem))
                ErrorEntity.CommonError.EmptyOrNullData -> setErrorMessage(ErrorMessage.ResourceId(R.string.error_empty_data))
                ErrorEntity.CommonError.NoInternetConnection -> callOnNoInternetConnection()
                ErrorEntity.CommonError.Unknown -> setErrorMessage(ErrorMessage.ResourceId(R.string.error_unknown_error_occured))
                else -> setErrorMessage(ErrorMessage.ResourceId(R.string.error_unknown_error_occured))
            }
        }
    }

    private fun callOnNoInternetConnection() {
        setErrorMessage(ErrorMessage.ResourceId(R.string.error_no_internet_connection))
    }


}