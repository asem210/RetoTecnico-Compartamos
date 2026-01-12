package com.example.domain.usecase.transaction

import android.util.Log
import com.example.domain.model.transaction.TransactionRequest
import com.example.domain.model.transaction.TransactionResult
import com.example.domain.repository.transaction.TransactionRepository
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * Caso de uso para realizar una transacción bancaria
 * Genera automáticamente un token de validación de 6 dígitos
 */
class CreateTransactionUseCase(
    private val repository: TransactionRepository
) {
    companion object {
        private const val TAG = "CreateTransactionUseCase"
    }

    suspend operator fun invoke(
        originAccountCode: String,
        destinationAccountCode: String,
        amount: Double,
        description: String?,
        currencyCode: String = "PEN",
        transactionType: String = "INTERNAL_TRANSFER"
    ): TransactionResult? {
        Log.d(TAG, "Iniciando transacción desde cuenta: $originAccountCode hacia: $destinationAccountCode")
        
        // Generar token de 6 dígitos
        val validationToken = generateValidationToken()
        Log.d(TAG, "Token de validación generado: $validationToken")

        delay(2000) // Simular delay de procesamiento

        val request = TransactionRequest(
            originAccountCode = originAccountCode,
            destinationAccountCode = destinationAccountCode,
            amount = amount,
            description = description,
            currencyCode = currencyCode,
            transactionType = transactionType,
            validationToken = validationToken
        )

        return try {
            val result = repository.createTransaction(request)
            if (result != null) {
                Log.d(TAG, "Transacción exitosa. Código de operación: ${result.operationCode}")
            } else {
                Log.e(TAG, "Error al realizar la transacción")
            }
            result
        } catch (e: Exception) {
            Log.e(TAG, "Excepción al realizar la transacción", e)
            null
        }
    }

    /**
     * Genera un token de validación de 6 dígitos
     */
    private fun generateValidationToken(): String {
        return Random.nextInt(100000, 999999).toString()
    }
}
