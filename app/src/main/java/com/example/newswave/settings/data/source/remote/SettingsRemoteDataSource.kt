package com.example.newswave.settings.data.source.remote

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.newswave.core.util.Error
import com.example.newswave.core.util.Result
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class SettingsRemoteDataSource @Inject constructor(
    val auth:FirebaseAuth
) {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend fun signOut():Flow<Result<Boolean,Error>> {
        return flow {
            try {
                auth.signOut()
                emit(Result.Success(true))
            }catch (e: HttpException) {
                emit(Result.Error(Error.Network.UNKNOWN))
            } catch (e: IOException) {
                emit(Result.Error(Error.Network.NO_INTERNET))
            } catch (e: Exception) {
                emit(Result.Error(Error.Network.UNKNOWN))
            }
        }
    }
}