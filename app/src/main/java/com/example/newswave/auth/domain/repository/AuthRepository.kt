package com.example.newswave.auth.domain.repository


import com.example.newswave.core.util.Error
import com.example.newswave.core.util.Result
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val currentUser: FirebaseUser?

    suspend fun signUserWithOneTap(): Flow<Result<BeginSignInResult,Error>>

   suspend fun signUserWithCredential(googleCredential: AuthCredential): Flow<Result<Boolean,Error>>

}