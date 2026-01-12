package com.example.exampleproject.features.transaction.form

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.transaction.CreateTransactionUseCase
import com.example.exampleproject.core.presentation.viewmodel.BaseViewModel
import com.example.exampleproject.core.utils.toAmountDouble
import kotlinx.coroutines.launch

class FormViewModel(
    private val createTransactionUseCase: CreateTransactionUseCase
) : BaseViewModel<FormUiState, FormIntent, FormNavigation>() {


    override fun createInitialState() = FormUiState()

    override suspend fun handleIntent(intent: FormIntent) {
        when (intent) {
            FormIntent.BackClicked -> goNavigation(FormNavigation.Back)
            is FormIntent.DestinationAccountCodeChanged -> setUiState { copy(destinationAccountCode = intent.accountCode) }
            is FormIntent.AmountChanged -> {
                val transactionAmount = intent.amount.toAmountDouble()
                val hasInsufficientFunds = transactionAmount > intent.availableBalance && intent.amount.isNotEmpty()
                
                setUiState { 
                    copy(
                        amount = intent.amount,
                        insufficientFundsError = hasInsufficientFunds,
                        errorMessage = if (hasInsufficientFunds) {
                            "Saldo insuficiente. Disponible: S/ ${String.format("%.2f", intent.availableBalance)}"
                        } else null
                    )
                }
            }
            is FormIntent.DescriptionChanged -> setUiState { copy(description = intent.description) }
            is FormIntent.CreateTransaction -> createTransaction(intent.originAccount)
            FormIntent.ContinueClicked -> {
                val result = uiState.value.transactionResult
                if (result != null) {
                    goNavigation(FormNavigation.GoToVoucher(result))
                }
            }
        }
    }

    private fun createTransaction(originAccount: com.example.domain.model.account.Account) {
        viewModelScope.launch {
            val state = uiState.value
            val transactionAmount = state.amount.toAmountDouble()

            // Validar saldo disponible
            if (transactionAmount > originAccount.availableBalance) {
                Log.d("FormViewModel", "Saldo insuficiente: $transactionAmount > ${originAccount.availableBalance}")
                setUiState {
                    copy(
                        insufficientFundsError = true,
                        errorMessage = "Saldo insuficiente. Disponible: ${originAccount.currencySymbol} ${String.format("%.2f", originAccount.availableBalance)}"
                    )
                }
                return@launch
            }

            Log.d("FormViewModel", "Iniciando creación de transacción")
            setUiState { copy(isLoading = true, errorMessage = null, insufficientFundsError = false) }

            val result = createTransactionUseCase(
                originAccountCode = originAccount.accountCode,
                destinationAccountCode = state.destinationAccountCode,
                amount = transactionAmount,
                description = state.description.ifBlank { null },
                currencyCode = originAccount.currencyCode,
                transactionType = "INTERNAL_TRANSFER"
            )

            if (result != null) {
                Log.d("FormViewModel", "Transacción exitosa: ${result.operationCode}")
                setUiState {
                    copy(
                        isLoading = false, 
                        transactionResult = result,
                        errorMessage = null,
                        insufficientFundsError = false
                    ) 
                }
            } else {
                Log.d("FormViewModel", "Error al crear la transacción")

                setUiState {
                    copy(
                        isLoading = false, 
                        errorMessage = "Error al procesar la transacción. Intente nuevamente.",
                        insufficientFundsError = false
                    ) 
                }
            }
        }
    }
}
