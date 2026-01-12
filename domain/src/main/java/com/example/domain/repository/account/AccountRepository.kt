package com.example.domain.repository.account

import com.example.domain.model.account.Account

/**
 * Repository interface para operaciones de cuentas
 */
interface AccountRepository {
    
    /**
     * Obtiene las cuentas por ID de usuario
     * @param id ID del usuario
     * @return Lista de cuentas o null si hay error
     */
    suspend fun getAccountsById(id: String): List<Account>?
}
