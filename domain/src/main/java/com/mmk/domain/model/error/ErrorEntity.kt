package com.mmk.domain.model.error

sealed class ErrorEntity {

    sealed class CommonError : ErrorEntity() {
        object NoInternetConnection : CommonError()
        object EmptyOrNullData : CommonError()
        object Unknown : CommonError()

    }

    sealed class ApiError : ErrorEntity() {
        object ServerProblem : ApiError()
        data class Other(val errorMessage: String? = "") : ApiError()
    }
}
