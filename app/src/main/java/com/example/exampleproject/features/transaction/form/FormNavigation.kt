package com.example.exampleproject.features.transaction.form

sealed class FormNavigation {
    data object Back : FormNavigation()
    data object Continue : FormNavigation()
}
