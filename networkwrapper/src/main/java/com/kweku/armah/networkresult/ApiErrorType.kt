package com.kweku.armah.networkresult

enum class ApiErrorType(val code: Int) {
    NOT_FOUND(404),
    EXCEPTION(-1);

    companion object {
        fun get(code: Int?): ApiErrorType? {
            return values().firstOrNull { it.code == code }
        }
    }
}