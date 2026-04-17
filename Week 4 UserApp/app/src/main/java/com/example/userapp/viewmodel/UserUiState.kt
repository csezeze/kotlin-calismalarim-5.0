package com.example.userapp.viewmodel

import com.example.userapp.data.model.User

sealed interface UserUiState {
    data object Loading : UserUiState
    data class Success(val users: List<User>) : UserUiState
    data class Error(val message: String) : UserUiState
}