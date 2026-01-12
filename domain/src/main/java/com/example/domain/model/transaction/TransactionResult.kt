package com.example.domain.model.transaction

import kotlinx.serialization.Serializable

/**
 * Modelo de dominio para el resultado de una transacción
 */
@Serializable
data class TransactionResult(
    val operationCode: String,        // Código único de operación (ej: "OP-123456")
    val operationDate: String,        // Fecha (ej: "12/01/2026")
    val operationTime: String,        // Hora (ej: "14:30:45")
    val originAccountCode: String,
    val destinationAccountCode: String,
    val amount: Double,
    val currencySymbol: String,
    val totalAmount: Double,          // Podría incluir comisiones
    val commission: Double = 0.0,
    val description: String?
)
