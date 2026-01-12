package com.example.data.repository.transaction

import com.example.data.datasource.transaction.TransactionDataSource
import com.example.data.dto.transaction.toDto
import com.example.data.dto.transaction.toDomain
import com.example.domain.model.transaction.TransactionRequest
import com.example.domain.model.transaction.TransactionResult
import com.example.domain.repository.transaction.TransactionRepository

/**
 * Implementación del repositorio de transacciones
 */
class TransactionRepositoryImpl(
    private val dataSource: TransactionDataSource
) : TransactionRepository {

    override suspend fun createTransaction(request: TransactionRequest): TransactionResult? {
        val response = dataSource.createTransaction(request.toDto())

        // Si data es null o success es false, retornamos null
        return if (response.data != null && response.success == true) {
            response.data.toDomain()
        } else {
            null
        }
    }
}
