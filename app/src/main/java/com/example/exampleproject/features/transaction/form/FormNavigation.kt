package com.example.exampleproject.features.transaction.form

import com.example.domain.model.transaction.TransactionResult

sealed class FormNavigation {
    data object Back : FormNavigation()
    data class GoToVoucher(val result: TransactionResult) : FormNavigation()
}
