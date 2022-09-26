package com.kweku.armah.networkresult

fun <T, C> ApiResult<T>.mapResult(mapper: (T) -> C): ApiResult<C> {
    return when (this) {
        is ApiResult.ApiError -> ApiResult.ApiError(type)
        is ApiResult.ApiSuccess -> ApiResult.ApiSuccess(mapper(data))
    }
}
