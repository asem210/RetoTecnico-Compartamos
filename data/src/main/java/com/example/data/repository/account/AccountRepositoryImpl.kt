package com.example.data.repository.account

import com.example.data.datasource.account.AccountDataSource
import com.example.data.dto.account.toDomain
import com.example.domain.model.account.Account
import com.example.domain.repository.account.AccountRepository

/**
 * Implementación del repositorio de cuentas
 */
class AccountRepositoryImpl(
    private val dataSource: AccountDataSource
) : AccountRepository {
    
    override suspend fun getAccountsById(id: String): List<Account>? {
        val response = dataSource.getAccountsById(id)
        
        // Si data es null o success es false, retornamos null
        return if (response.data != null && response.success == true) {
            response.data.map { it.toDomain() }
        } else {
            null
        }
    }
}
