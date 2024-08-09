package com.example.newswave.auth.di

import android.content.Context
import com.example.newswave.BuildConfig
import com.example.newswave.auth.data.repository.AuthRepositoryImpl
import com.example.newswave.auth.data.source.remote.AuthRemoteDataSource
import com.example.newswave.auth.domain.repository.AuthRepository
import com.example.newswave.auth.domain.use_case.GetCurrentUserUseCase
import com.example.newswave.auth.domain.use_case.OneTapSignInUseCase
import com.example.newswave.auth.domain.use_case.SignUserWithCredentialUseCase
import com.example.newswave.core.util.SIGN_IN_REQUEST
import com.example.newswave.core.util.SIGN_UP_REQUEST
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Singleton
    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Singleton
    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore


    @Singleton
    @Provides
    @Named(SIGN_IN_REQUEST)
    fun provideSignInRequest() = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(BuildConfig.WEB_CLIENT_ID)
                .setFilterByAuthorizedAccounts(true)
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()

    @Singleton
    @Provides
    @Named(SIGN_UP_REQUEST)
    fun provideSignUpRequest() = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(BuildConfig.WEB_CLIENT_ID)
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .build()

    @Singleton
    @Provides
    fun provideOneTapClient(
        @ApplicationContext
        context: Context
    ) = Identity.getSignInClient(context)

    @Provides
    @Singleton
    fun provideSignUserWithCredentialUseCase(authRepository: AuthRepository): SignUserWithCredentialUseCase {
        return SignUserWithCredentialUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideGetCurrentUserUseCase(authRepository: AuthRepository): GetCurrentUserUseCase {
        return GetCurrentUserUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideOneTapSignInUseCase(authRepository: AuthRepository): OneTapSignInUseCase {
        return OneTapSignInUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(
        auth: FirebaseAuth,
        oneTapClient: SignInClient,
        @Named(SIGN_IN_REQUEST)
        signUpRequest: BeginSignInRequest,
        @Named(SIGN_UP_REQUEST)
        signInRequest: BeginSignInRequest,
        firestore: FirebaseFirestore
    ): AuthRemoteDataSource {
        return AuthRemoteDataSource(oneTapClient, signUpRequest, signInRequest, auth, firestore)
    }

}

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

}