package com.example.exampleproject.features.transaction.form

sealed class FormIntent {
    data object BackClicked : FormIntent()
    data class AmountChanged(val amount: String) : FormIntent()
    data class DescriptionChanged(val description: String) : FormIntent()
    data object ContinueClicked : FormIntent()
}
