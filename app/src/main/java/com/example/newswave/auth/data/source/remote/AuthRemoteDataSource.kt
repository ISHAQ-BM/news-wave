package com.example.newswave.auth.data.source.remote

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.newswave.core.util.Error
import com.example.newswave.core.util.Result
import com.example.newswave.core.util.SIGN_IN_REQUEST
import com.example.newswave.core.util.SIGN_UP_REQUEST
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

class AuthRemoteDataSource @Inject constructor(
    private val oneTapClient: SignInClient,
    @Named(SIGN_IN_REQUEST)
    private val signUpRequest: BeginSignInRequest,
    @Named(SIGN_UP_REQUEST)
    private val signInRequest: BeginSignInRequest,
    private val auth: FirebaseAuth,
){
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend fun signUserWithOneTap(): Flow<Result<BeginSignInResult, Error>>{
        return flow {
            try {

                val signInResult = oneTapClient.beginSignIn(signInRequest).await()
                emit(Result.Success(signInResult))
            } catch (e: Exception) {
                try {
                    val signUpResult = oneTapClient.beginSignIn(signUpRequest).await()
                    emit(Result.Success(signUpResult))
                } catch (e: ApiException) {
                    emit(Result.Error(Error.Authentication.NO_EMAIL_ON_DEVICE))
                } catch (e: HttpException) {
                    emit(Result.Error(Error.Network.UNKNOWN))
                } catch (e: IOException) {
                    emit(Result.Error(Error.Network.NO_INTERNET))
                } catch (e: Exception) {
                    emit(Result.Error(Error.Network.UNKNOWN))
                }
            }
        }
    }

    suspend fun signUserWithCredential(googleCredential: AuthCredential): Flow<Result<Boolean, Error>> {
        return flow {
            try {
                val authResult = auth.signInWithCredential(googleCredential).await()
                val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
//                if (isNewUser) {
//                    addUserToFirestore()
//                }
                emit(Result.Success(true))
            } catch (e: FirebaseAuthException) {
                emit(Result.Error(Error.Authentication.UNKNOWN))
            }
        }
    }
}