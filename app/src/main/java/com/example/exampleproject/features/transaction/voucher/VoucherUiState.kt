package com.example.exampleproject.features.transaction.voucher

import androidx.compose.runtime.Immutable
import com.example.domain.model.account.Account

@Immutable
data class VoucherUiState(
    val account: Account? = null,
    val amount: String = "",
    val description: String = "",
    val transactionId: String = "",
    val transactionDate: String = ""
)
