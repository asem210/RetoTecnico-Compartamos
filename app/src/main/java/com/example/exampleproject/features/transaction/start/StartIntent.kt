package com.example.exampleproject.features.transaction.start

import com.example.domain.model.account.Account

sealed class StartIntent {
    data object LoadAccounts : StartIntent()
    data object BackClicked : StartIntent()
    data class AccountSelected(val account: Account) : StartIntent()
    data object ContinueClicked : StartIntent()
    data class UpdateAccountBalance(val accountCode: String, val amount: Double) : StartIntent()
}
