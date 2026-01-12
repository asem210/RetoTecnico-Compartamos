package com.example.domain.usecase.account

import android.util.Log
import com.example.domain.model.account.Account
import com.example.domain.repository.account.AccountRepository
import kotlinx.coroutines.delay

/**
 * Caso de uso para obtener cuentas por ID de usuario
 */
class GetAccountByIdUseCase(
    private val repository: AccountRepository
) {
    companion object {
        private const val TAG = "GetAccountByIdUseCase"
    }
    
    /**
     * Ejecuta el caso de uso
     * @param userId ID del usuario
     * @return Lista de cuentas o null si hay error
     */
    suspend operator fun invoke(userId: String): List<Account>? {
        Log.d(TAG, "Obteniendo cuentas para el usuario: $userId")
        
        // Simular delay de carga (3 segundos)
        delay(3000)
        
        val accounts = repository.getAccountsById(userId)
        
        return if (accounts != null) {
            Log.d(TAG, "Cuentas obtenidas exitosamente: ${accounts.size} cuenta(s)")
            accounts
        } else {
            Log.e(TAG, "Error al obtener cuentas para el usuario: $userId")
            null
        }
    }
}
