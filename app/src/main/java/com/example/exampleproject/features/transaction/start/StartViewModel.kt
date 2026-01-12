package com.example.exampleproject.features.transaction.start

import com.example.domain.usecase.account.GetAccountByIdUseCase
import com.example.exampleproject.core.presentation.viewmodel.BaseViewModel

class StartViewModel(
    private val getAccountByIdUseCase: GetAccountByIdUseCase
) : BaseViewModel<StartUiState, StartIntent, StartNavigation>() {

    override fun createInitialState() = StartUiState()

    override suspend fun handleIntent(intent: StartIntent) {
        when (intent) {
            is StartIntent.LoadAccounts -> loadAccounts()
            is StartIntent.AccountSelected -> setUiState {
                copy(accountSelected = intent.account, accountFocused = false)
            }
            StartIntent.BackClicked -> goNavigation(StartNavigation.Back)
            StartIntent.ContinueClicked -> navigateToForm()
        }
    }

    private suspend fun loadAccounts() {
        setUiState { copy(isLoading = true) }

        val accounts = getAccountByIdUseCase("12345")

        setUiState {
            copy(
                isLoading = false,
                accounts = accounts ?: emptyList()
            )
        }
    }

    private fun navigateToForm() {
        val account = uiState.value.accountSelected
        if (account != null) {
            goNavigation(StartNavigation.GoToForm(account))
        }
    }

    fun toggleAccountFocus(focused: Boolean) {
        setUiState { copy(accountFocused = focused) }
    }
}
