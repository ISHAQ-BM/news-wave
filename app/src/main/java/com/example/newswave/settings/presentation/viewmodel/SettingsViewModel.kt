package com.example.newswave.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newswave.core.util.Result
import com.example.newswave.settings.domain.use_case.SignOutUseCase
import com.example.newswave.settings.presentation.ui.state.SettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase
) :ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState : StateFlow<SettingsUiState> = _uiState

    fun signOut(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            signOutUseCase().collect{result ->
                when(result){
                    is Result.Error -> TODO()
                    is Result.Success -> _uiState.update {
                        it.copy(
                            isLoading = false,
                            isSignOut = true
                        )
                    }
                }

            }
        }
    }

}