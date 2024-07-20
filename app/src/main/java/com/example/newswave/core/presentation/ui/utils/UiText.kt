package com.example.newswave.core.presentation.ui.utils

import android.content.Context
import androidx.annotation.StringRes
import com.example.newswave.R
import com.example.newswave.core.util.Error

class UiText (
        @StringRes val id: Int
    ){

    fun asString(context:Context) :String{
        return context.getString(id)

    }
}
fun Error.asUiText(): UiText {
    return when (this) {
        Error.Network.REQUEST_TIMEOUT -> UiText(
            R.string.the_request_timed_out
        )

        Error.Network.TOO_MANY_REQUESTS -> UiText(
            R.string.youve_hit_your_rate_limit
        )

        Error.Network.NO_INTERNET -> UiText(
            R.string.no_internet
        )

        Error.Network.PAYLOAD_TOO_LARGE -> UiText(
            R.string.file_too_large
        )

        Error.Network.SERVER_ERROR -> UiText(
            R.string.server_error
        )

        Error.Network.SERIALIZATION -> UiText(
            R.string.error_serialization
        )

        Error.Network.UNKNOWN -> UiText(
            R.string.unknown_error
        )

        Error.Local.DISK_FULL -> UiText(
            R.string.error_disk_full
        )

        Error.Authentication.UNAUTHORIZED -> TODO()
        Error.Authentication.FORBIDDEN -> TODO()
        Error.Authentication.TOKEN_EXPIRED -> TODO()
        Error.Authentication.INVALID_CREDENTIALS -> TODO()
        Error.Authentication.ACCOUNT_LOCKED -> TODO()
        Error.Authentication.UNKNOWN -> TODO()
        Error.Network.UNAUTHORIZED -> TODO()
        Error.Network.FORBIDDEN -> TODO()
        Error.Network.NOT_FOUND -> TODO()
        Error.Network.BAD_REQUEST -> TODO()
        Error.Network.CONFLICT -> TODO()
        Error.Network.GONE -> TODO()
        Error.Network.UNPROCESSABLE_ENTITY -> UiText(R.string.no_internet)
        Error.ValidationError.INVALID_INPUT -> TODO()
        Error.ValidationError.MISSING_FIELD -> TODO()
        Error.ValidationError.OUT_OF_RANGE -> TODO()
        Error.ValidationError.PATTERN_MISMATCH -> TODO()
        Error.ValidationError.UNKNOWN -> TODO()
        Error.Local.UNKNOWN -> TODO()
        Error.Authentication.NO_EMAIL_ON_DEVICE -> TODO()
    }
}