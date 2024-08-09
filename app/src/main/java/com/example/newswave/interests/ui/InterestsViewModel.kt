package com.example.newswave.interests.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newswave.core.presentation.ui.utils.asUiText
import com.example.newswave.core.util.Result
import com.example.newswave.interests.domain.use_case.GetInterestsUseCase
import com.example.newswave.interests.domain.use_case.SaveInterestsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterestsViewModel @Inject constructor(
    val getInterestsUseCase: GetInterestsUseCase,
    val saveInterestsUseCase: SaveInterestsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(InterestsUiState())
    val uiState: StateFlow<InterestsUiState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            getInterestsUseCase().collect { result ->
                when (result) {
                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                generalMessage = result.error.asUiText(),
                                isLoading = false
                            )
                        }
                    }

                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                modifiedInterests = result.data,
                                originalInterests = result.data,
                                isLoading = false
                            )
                        }
                    }


                }
            }
        }
    }


    fun interestClicked(interest: String, isChecked: Boolean) {
        _uiState.update {
            val modifiedInterests = it.modifiedInterests.toMutableList()
            if (isChecked)
                modifiedInterests.remove(interest)
            else
                modifiedInterests.add(interest)
            it.copy(
                modifiedInterests = modifiedInterests
            )
        }
    }

    fun saveInterests() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            saveInterestsUseCase(_uiState.value.modifiedInterests).collect { result ->
                when (result) {
                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }

                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                saveSuccessful = true,
                                originalInterests = it.modifiedInterests

                            )
                        }
                    }


                }
            }
        }
    }


}