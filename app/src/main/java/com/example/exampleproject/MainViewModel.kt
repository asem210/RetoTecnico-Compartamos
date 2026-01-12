package com.example.exampleproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.account.Account
import com.example.domain.usecase.account.GetAccountByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla principal
 */
class MainViewModel(
    private val getAccountByIdUseCase: GetAccountByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        loadAccounts()
    }

    private fun loadAccounts() {
        viewModelScope.launch {
            _uiState.value = MainUiState.Loading
            
            // Llamar al use case con un ID de prueba
            val accounts = getAccountByIdUseCase("12345")
            
            _uiState.value = if (accounts != null) {
                MainUiState.Success(accounts)
            } else {
                MainUiState.Error("No se pudieron cargar las cuentas")
            }
        }
    }

    fun retry() {
        loadAccounts()
    }
}

/**
 * Estados de la UI
 */
sealed class MainUiState {
    data object Loading : MainUiState()
    data class Success(val accounts: List<Account>) : MainUiState()
    data class Error(val message: String) : MainUiState()
}
