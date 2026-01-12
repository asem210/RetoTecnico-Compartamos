package com.example.data.dto.transaction

import com.example.domain.model.transaction.TransactionResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionResponseDto(
    @SerialName("codigoOperacion")
    val operationCode: String? = null,
    @SerialName("fechaOperacion")
    val operationDate: String? = null,
    @SerialName("horaOperacion")
    val operationTime: String? = null,
    @SerialName("codigoCuentaOrigen")
    val originAccountCode: String? = null,
    @SerialName("codigoCuentaDestino")
    val destinationAccountCode: String? = null,
    @SerialName("montoTransaccion")
    val transactionAmount: Double = 0.0,
    @SerialName("simboloMoneda")
    val currencySymbol: String? = null,
    @SerialName("montoTotal")
    val totalAmount: Double = 0.0,
    @SerialName("comision")
    val commission: Double = 0.0,
    @SerialName("descripcion")
    val description: String? = null
)

/**
 * Extensión para convertir un TransactionResponseDto a modelo de dominio
 */
fun TransactionResponseDto.toDomain(): TransactionResult {
    return TransactionResult(
        operationCode = operationCode ?: "",
        operationDate = operationDate ?: "",
        operationTime = operationTime ?: "",
        originAccountCode = originAccountCode ?: "",
        destinationAccountCode = destinationAccountCode ?: "",
        amount = transactionAmount,
        currencySymbol = currencySymbol ?: "S/",
        totalAmount = totalAmount,
        commission = commission,
        description = description
    )
}
