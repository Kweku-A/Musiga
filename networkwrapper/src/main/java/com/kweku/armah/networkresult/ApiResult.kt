package com.kweku.armah.networkresult

sealed class ApiResult<T> {
    data class ApiSuccess<T>(val data: T) : ApiResult<T>()
    data class ApiError<T>(val type: ApiErrorType) : ApiResult<T>()
}
