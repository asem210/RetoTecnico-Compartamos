package com.example.data.datasource.transaction

import com.example.data.dto.BaseResponse
import com.example.data.dto.transaction.TransactionRequestDto
import com.example.data.dto.transaction.TransactionResponseDto
import com.example.data.remote.transaction.TransactionApiService
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

/**
 * Data Source para operaciones de transacciones
 * Incluye datos mockeados para simular transacciones exitosas
 */
class TransactionDataSource(
    private val apiService: TransactionApiService
) {
    /**
     * Crea una transacción bancaria
     * @param request Datos de la transacción
     * @return Respuesta con datos de la transacción (mockeada si falla la API)
     */
    suspend fun createTransaction(request: TransactionRequestDto): BaseResponse<TransactionResponseDto> {
        return try {
            apiService.createTransaction(request)
        } catch (e: Exception) {
            // Retornar datos mockeados con transacción exitosa
            BaseResponse(
                data = getMockedTransaction(request),
                success = true,
                message = "Transacción procesada exitosamente (Mock)",
                codeMessage = "TRANSACTION_SUCCESS",
                typeMessage = 0
            )
        }
    }

    /**
     * Genera una respuesta de transacción mockeada
     */
    private fun getMockedTransaction(request: TransactionRequestDto): TransactionResponseDto {
        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        val operationCode = "OP-${Random.nextInt(100000, 999999)}"

        return TransactionResponseDto(
            operationCode = operationCode,
            operationDate = currentDate,
            operationTime = currentTime,
            originAccountCode = request.data.originAccountCode,
            destinationAccountCode = request.data.destinationAccountCode,
            transactionAmount = request.data.transactionAmount,
            currencySymbol = "S/",
            totalAmount = request.data.transactionAmount, // Sin comisión en este mock
            commission = 0.0,
            description = request.data.description
        )
    }
}
