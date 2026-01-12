package com.example.exampleproject.features.transaction.start

import androidx.compose.runtime.Immutable
import com.example.domain.model.account.Account

@Immutable
data class StartUiState(
    val accountFocused: Boolean = false,
    val accountSelected: Account? = null,
    val isLoading: Boolean = false,
    val accounts: List<Account> = emptyList()
)
