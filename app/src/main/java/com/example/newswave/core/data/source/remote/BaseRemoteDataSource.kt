package com.example.newswave.core.data.source.remote

import com.example.newswave.core.util.Error
import com.example.newswave.core.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRemoteDataSource {
    suspend fun <D> safeApiCall(apiToBeCalled: suspend () -> Response<D>): Flow<Result<D, Error>> = flow{
        try {
            val response = apiToBeCalled()
            if (response.isSuccessful) {
                emit(Result.Success(data = response.body()!!))
            } else {
                val error = getErrorFromStatusCode(response.code())
                emit(Result.Error(error))
            }
        } catch (e: HttpException) {
            val error = getErrorFromStatusCode(e.code())
            emit(Result.Error(error))
        } catch (e: IOException) {
            emit(Result.Error(Error.Network.NO_INTERNET))
        } catch (e: Exception) {
            emit(Result.Error(Error.Network.UNKNOWN))
        }
    }

}

private fun getErrorFromStatusCode(statusCode: Int): Error{
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




