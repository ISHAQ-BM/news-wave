package com.example.newswave.auth.domain.use_case

import com.example.newswave.auth.domain.repository.AuthRepository
import javax.inject.Inject

class OneTapSignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke()=authRepository.signUserWithOneTap()

}