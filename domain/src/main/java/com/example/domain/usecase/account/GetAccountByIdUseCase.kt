package com.example.domain.usecase.account

import co.touchlab.kermit.Logger
import com.example.domain.model.account.Account
import com.example.domain.repository.account.AccountRepository
import kotlinx.coroutines.delay

/**
 * Caso de uso para obtener cuentas por ID de usuario
 */
class GetAccountByIdUseCase(
    private val repository: AccountRepository
) {
    private val logger = Logger.withTag("GetAccountByIdUseCase")
    
    /**
     * Ejecuta el caso de uso
     * @param userId ID del usuario
     * @return Lista de cuentas o null si hay error
     */
    suspend operator fun invoke(userId: String): List<Account>? {
        logger.d { "Obteniendo cuentas para el usuario: $userId" }
        
        // Simular delay de carga (3 segundos)
        delay(3000)
        
        val accounts = repository.getAccountsById(userId)
        
        return if (accounts != null) {
            logger.d { "Cuentas obtenidas exitosamente: ${accounts.size} cuenta(s)" }
            accounts
        } else {
            logger.e { "Error al obtener cuentas para el usuario: $userId" }
            null
        }
    }
}
