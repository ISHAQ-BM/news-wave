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

        Error.Authentication.UNAUTHORIZED -> UiText(R.string.error_unauthorized)
        Error.Authentication.FORBIDDEN -> UiText(R.string.error_forbidden)
        Error.Authentication.TOKEN_EXPIRED -> UiText(R.string.error_token_expired)
        Error.Authentication.INVALID_CREDENTIALS -> UiText(R.string.error_invalid_credentials)
        Error.Authentication.ACCOUNT_LOCKED -> UiText(R.string.error_account_locked)
        Error.Authentication.UNKNOWN -> UiText(R.string.error_authentication_unknown)
        Error.Network.UNAUTHORIZED -> UiText(R.string.error_network_unauthorized)
        Error.Network.FORBIDDEN -> UiText(R.string.error_network_forbidden)
        Error.Network.NOT_FOUND -> UiText(R.string.error_network_not_found)
        Error.Network.BAD_REQUEST -> UiText(R.string.error_network_bad_request)
        Error.Network.CONFLICT -> UiText(R.string.error_network_conflict)
        Error.Network.GONE -> UiText(R.string.error_network_gone)
        Error.Network.UNPROCESSABLE_ENTITY -> UiText(R.string.error_network_unprocessable_entity)
        Error.Network.NO_INTERNET -> UiText(R.string.no_internet)
        Error.ValidationError.INVALID_INPUT -> UiText(R.string.error_invalid_input)
        Error.ValidationError.MISSING_FIELD -> UiText(R.string.error_missing_field)
        Error.ValidationError.OUT_OF_RANGE -> UiText(R.string.error_out_of_range)
        Error.ValidationError.PATTERN_MISMATCH -> UiText(R.string.error_pattern_mismatch)
        Error.ValidationError.UNKNOWN -> UiText(R.string.error_validation_unknown)
        Error.Local.UNKNOWN -> UiText(R.string.error_local_unknown)
        Error.Authentication.NO_EMAIL_ON_DEVICE -> UiText(R.string.error_no_email_on_device)

    }
}