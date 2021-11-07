package com.mmk.domain.model

import com.mmk.domain.model.error.ErrorEntity

sealed class Result<out T> {

    data class Success<T>(val data: T) : Result<T>()
    data class Error(val errorEntity: ErrorEntity? = null) : Result<Nothing>()

}

inline fun <T : Any> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    if (this is Result.Success) action(data)
    return this
}

inline fun <T : Any> Result<T>.onError(action: (errorEntity: ErrorEntity?) -> Unit): Result<T> {
    if (this is Result.Error) action(errorEntity)
    return this
}

