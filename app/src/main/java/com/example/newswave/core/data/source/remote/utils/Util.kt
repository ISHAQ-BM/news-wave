package com.example.newswave.core.data.source.remote.utils

import com.example.newswave.core.util.Error

object Util {
    fun getErrorFromStatusCode(statusCode: Int): Error.Network{
        return when (statusCode) {
            400 -> Error.Network.BAD_REQUEST
            401 -> Error.Network.UNAUTHORIZED
            403 -> Error.Network.FORBIDDEN
            404 -> Error.Network.NOT_FOUND
            408 -> Error.Network.REQUEST_TIMEOUT
            409 -> Error.Network.CONFLICT
            410 -> Error.Network.GONE
            413 -> Error.Network.PAYLOAD_TOO_LARGE
            422 -> Error.Network.UNPROCESSABLE_ENTITY
            429 -> Error.Network.TOO_MANY_REQUESTS
            in 500..599 -> Error.Network.SERVER_ERROR
            else -> Error.Network.UNKNOWN
        }
    }
}