package com.kweku.armah.networkresult

enum class ApiErrorType(val message: String) {
    NETWORK_ERROR("A network error occurred"),
    EXCEPTION("An exception occurred");

    companion object {
        fun get(message: String): ApiErrorType {
            return values().first { it.message == message }
        }
    }
}
