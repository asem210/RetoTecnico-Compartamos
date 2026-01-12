package com.example.exampleproject.features.transaction.start

import com.example.domain.model.account.Account

sealed class StartNavigation {
    data object Back : StartNavigation()
    data class GoToForm(val account: Account) : StartNavigation()
}
