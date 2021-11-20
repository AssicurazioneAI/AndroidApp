package com.mmk.domain.model.error

import java.lang.Exception
import kotlin.math.E

sealed class ErrorEntity {

    sealed class CommonError : ErrorEntity() {
        object NoInternetConnection : CommonError()
        object EmptyOrNullData : CommonError()
        data class Unknown(val exception: Exception? = null) : CommonError()

    }

    sealed class ApiError : ErrorEntity() {
        data class ServerProblem(val exception: Exception) : ApiError()
        data class Other(val errorMessage: String? = "", val errorCode: Int? = null) : ApiError()
    }

    sealed class ImageError : ErrorEntity() {
        object WrongFormat : ImageError()
    }

}



