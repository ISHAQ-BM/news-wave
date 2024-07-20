package com.example.newswave.auth.domain.use_case

import com.example.newswave.auth.domain.repository.AuthRepository
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthCredential
import javax.inject.Inject

class SignUserWithCredentialUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(googleCredential: AuthCredential)=authRepository.signUserWithCredential(googleCredential)
}