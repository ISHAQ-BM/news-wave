package com.example.newswave.interests.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newswave.core.util.Result
import com.example.newswave.interests.domain.use_case.GetInterestsUseCase
import com.example.newswave.interests.domain.use_case.UpdateInterestsUseCase
import com.example.newswave.interests.presentation.ui.state.InterestsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterestsViewModel @Inject constructor(
    val getInterestsUseCase: GetInterestsUseCase,
    val updateInterestsUseCase: UpdateInterestsUseCase
):ViewModel() {
    private val _uiState = MutableStateFlow(InterestsUiState())
    val uiState : StateFlow<InterestsUiState> = _uiState

    init {
        viewModelScope.launch {
            getInterestsUseCase().collect{result ->
                when(result){
                    is Result.Error -> {}
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                updateSuccessful = true,
                                interestsList = result.data.toMutableList(),
                                previousInterests = result.data
                            )
                        }
                    }


                }
            }
        }
    }



    fun interestClicked(interest:String){
        _uiState.update {
            val newInterestsList = it.interestsList.toMutableList()
            if (newInterestsList.contains(interest))
                newInterestsList.remove(interest)
            else
                newInterestsList.add(interest)
            it.copy(
                interestsList = newInterestsList
            )
        }
    }

    fun updateInterests(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            updateInterestsUseCase(_uiState.value.interestsList).collect{result ->
                when(result){
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
                                previousInterests = it.interestsList
                            )
                        }
                    }


                }
            }
        }
    }



}