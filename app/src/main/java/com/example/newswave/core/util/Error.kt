package com.example.newswave.core.util

sealed interface Error {
    enum class Network: Error {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        UNAUTHORIZED,
        FORBIDDEN,
        NOT_FOUND,
        BAD_REQUEST,
        CONFLICT,
        GONE,
        UNPROCESSABLE_ENTITY,
        UNKNOWN
    }
    enum class Local: Error {
        DISK_FULL,
        UNKNOWN
    }

    enum class Authentication : Error {
        UNAUTHORIZED,
        FORBIDDEN,
        TOKEN_EXPIRED,
        INVALID_CREDENTIALS,
        ACCOUNT_LOCKED,
        UNKNOWN
    }

    enum class ValidationError : Error{
        INVALID_INPUT,
        MISSING_FIELD,
        OUT_OF_RANGE,
        PATTERN_MISMATCH,
        UNKNOWN
    }
}