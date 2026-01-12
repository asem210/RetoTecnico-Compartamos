package com.example.exampleproject.features.transaction.start

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.account.GetAccountByIdUseCase
import com.example.exampleproject.core.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class StartViewModel(
    private val getAccountByIdUseCase: GetAccountByIdUseCase
) : BaseViewModel<StartUiState, StartIntent, StartNavigation>() {

    companion object {
        private const val TAG = "StartViewModel"
    }

    private var accountsLoaded = false

    override fun createInitialState() = StartUiState()

    init {
        setIntent(StartIntent.LoadAccounts)
    }

    override suspend fun handleIntent(intent: StartIntent) {
        when (intent) {
            is StartIntent.LoadAccounts -> {
                if (!accountsLoaded) {
                    loadAccounts()
                }
            }
            is StartIntent.AccountSelected -> setUiState {
                copy(accountSelected = intent.account, accountFocused = false)
            }
            StartIntent.BackClicked -> goNavigation(StartNavigation.Back)
            StartIntent.ContinueClicked -> navigateToForm()
            is StartIntent.UpdateAccountBalance -> updateAccountBalance(intent.accountCode, intent.amount)
        }
    }

    private fun loadAccounts() {
        viewModelScope.launch {
            setUiState { copy(isLoading = true) }

            val accounts = getAccountByIdUseCase("12345")

            setUiState {
                copy(
                    isLoading = false,
                    accounts = accounts ?: emptyList()
                )
            }
            accountsLoaded = true
            Log.d(TAG, "Cuentas cargadas: ${accounts?.size ?: 0}")
        }
    }

    private fun updateAccountBalance(accountCode: String, transactionAmount: Double) {
        val currentAccounts = uiState.value.accounts
        val updatedAccounts = currentAccounts.map { account ->
            if (account.accountCode == accountCode) {
                val newBalance = account.availableBalance - transactionAmount
                Log.d(TAG, "Actualizando saldo de cuenta $accountCode: ${account.availableBalance} -> $newBalance")
                account.copy(availableBalance = newBalance)
            } else {
                account
            }
        }
        
        setUiState { 
            copy(
                accounts = updatedAccounts,
                accountSelected = null  // Limpiar selección
            ) 
        }
        Log.d(TAG, "Saldo actualizado exitosamente")
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
