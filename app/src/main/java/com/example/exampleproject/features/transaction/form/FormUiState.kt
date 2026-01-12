package com.example.exampleproject.features.transaction.form

import androidx.compose.runtime.Immutable

@Immutable
data class FormUiState(
    val amount: String = "",
    val description: String = "",
    val isLoading: Boolean = false
)
