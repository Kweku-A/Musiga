package com.kweku.armah.networkresult

import com.kweku.armah.networkresult.ApiResult.ApiError
import com.kweku.armah.networkresult.ApiResult.ApiSuccess
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

suspend inline fun <reified T> handleKtorApi(execute: () -> HttpResponse): ApiResult<T> {
    return try {
        val response = execute()
        if (response.status.isSuccess()) {
            val body: T = response.body()
            ApiSuccess(body ?: Unit as T)
        } else {
            ApiError(type = ApiErrorType.REQUEST_ERROR)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        ApiError(ApiErrorType.EXCEPTION)
    }
}
