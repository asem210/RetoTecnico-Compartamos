package com.example.domain.model.transaction

/**
 * Modelo de dominio para una solicitud de transacción
 */
data class TransactionRequest(
    val originAccountCode: String,
    val destinationAccountCode: String,
    val amount: Double,
    val description: String?,
    val currencyCode: String,
    val transactionType: String,
    val validationToken: String // Token de 6 dígitos generado en el UseCase
)
