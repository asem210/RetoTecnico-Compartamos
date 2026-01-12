package com.example.exampleproject.features.transaction.form

import com.example.domain.model.account.Account

sealed class FormIntent {
    data object BackClicked : FormIntent()
    data class DestinationAccountCodeChanged(val accountCode: String) : FormIntent()
    data class AmountChanged(val amount: String, val availableBalance: Double) : FormIntent()
    data class DescriptionChanged(val description: String) : FormIntent()
    data class CreateTransaction(val originAccount: Account) : FormIntent()
    data object ContinueClicked : FormIntent()
}
