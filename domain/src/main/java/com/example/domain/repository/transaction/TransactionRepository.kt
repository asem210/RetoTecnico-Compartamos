package com.example.domain.repository.transaction

import com.example.domain.model.transaction.TransactionRequest
import com.example.domain.model.transaction.TransactionResult

/**
 * Interfaz del repositorio para operaciones de transacciones.
 */
interface TransactionRepository {
    /**
     * Crea una transacción bancaria.
     * @param request Datos de la transacción.
     * @return Resultado de la transacción o null si hay un error.
     */
    suspend fun createTransaction(request: TransactionRequest): TransactionResult?
}
