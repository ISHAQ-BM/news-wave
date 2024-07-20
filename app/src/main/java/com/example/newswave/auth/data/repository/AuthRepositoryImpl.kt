package com.example.newswave.auth.data.repository

import com.example.newswave.auth.data.source.remote.AuthRemoteDataSource
import com.example.newswave.auth.domain.repository.AuthRepository
import com.example.newswave.core.util.Error
import com.example.newswave.core.util.Result
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource

) : AuthRepository {
    override suspend fun signUserWithOneTap(): Flow<Result<BeginSignInResult, Error>> = authRemoteDataSource.signUserWithOneTap()

    override suspend fun signUserWithCredential(googleCredential: AuthCredential): Flow<Result<Boolean, Error>> = authRemoteDataSource.signUserWithCredential(googleCredential)
}