package com.example.exampleproject.features.transaction.form

import com.example.exampleproject.core.presentation.viewmodel.BaseViewModel

class FormViewModel : BaseViewModel<FormUiState, FormIntent, FormNavigation>() {

    override fun createInitialState() = FormUiState()

    override suspend fun handleIntent(intent: FormIntent) {
        when (intent) {
            FormIntent.BackClicked -> goNavigation(FormNavigation.Back)
            is FormIntent.AmountChanged -> setUiState { copy(amount = intent.amount) }
            is FormIntent.DescriptionChanged -> setUiState { copy(description = intent.description) }
            FormIntent.ContinueClicked -> goNavigation(FormNavigation.Continue)
        }
    }
}
