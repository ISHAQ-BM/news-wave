package com.example.newswave.auth.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newswave.auth.domain.use_case.OneTapSignInUseCase
import com.example.newswave.auth.domain.use_case.SignUserWithCredentialUseCase
import com.example.newswave.auth.presentation.ui.state.SignUserUiState
import com.example.newswave.core.presentation.ui.utils.asUiText
import com.example.newswave.core.util.Result
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val oneTapClient: SignInClient,
    val signUserWithCredentialUseCase: SignUserWithCredentialUseCase,
    val oneTapSignInUseCase: OneTapSignInUseCase
):ViewModel() {

    private val _uiState = MutableStateFlow(SignUserUiState())
    val uiState : StateFlow<SignUserUiState> = _uiState


    fun signUserWithCredential(googleCredential: AuthCredential){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            signUserWithCredentialUseCase(googleCredential).collect{result ->
                when(result){
                    is Result.Error -> {
                        Log.d("login","${result}")
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                generalMessage = result.error.asUiText()
                            )
                        }
                    }
                    is Result.Success -> _uiState.update {
                        it.copy(
                            isNewUser = result.data,
                            isLoading = false,
                            isLoginSuccessful = true
                        )
                    }
                }
            }
        }
    }


    fun oneTapSignIn(googleLauncher:(signInResult: BeginSignInResult)->Unit){
        viewModelScope.launch {
            oneTapSignInUseCase().collect{result ->
                _uiState.update {
                    it.copy(
                        isLoading = true
                    )
                }
                when(result){
                    is Result.Error -> _uiState.update {
                        it.copy(
                            isLoading = false,
                            generalMessage = result.error.asUiText()
                        )
                    }
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                            )
                        }
                        googleLauncher(result.data)
                    }
                }
            }
        }
    }
}