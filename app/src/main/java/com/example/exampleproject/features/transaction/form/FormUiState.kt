package com.example.exampleproject.features.transaction.form

import androidx.compose.runtime.Immutable
import com.example.domain.model.transaction.TransactionResult

@Immutable
data class FormUiState(
    val destinationAccountCode: String = "",
    val amount: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val transactionResult: TransactionResult? = null,
    val errorMessage: String? = null,
    val insufficientFundsError: Boolean = false
)
