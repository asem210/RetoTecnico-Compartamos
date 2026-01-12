package com.example.data.dto.transaction

import com.example.domain.model.transaction.TransactionRequest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionRequestDto(
    @SerialName("data")
    val data: TransactionDataDto,
    @SerialName("data1")
    val data1: String? = null,
    @SerialName("data2")
    val data2: String? = null
)

@Serializable
data class TransactionDataDto(
    @SerialName("codigoCuentaOrigen")
    val originAccountCode: String,
    @SerialName("codigoCuentaDestino")
    val destinationAccountCode: String,
    @SerialName("montoTransaccion")
    val transactionAmount: Double,
    @SerialName("descripcion")
    val description: String? = null,
    @SerialName("codigoMoneda")
    val currencyCode: String,
    @SerialName("tipoTransaccion")
    val transactionType: String,
    @SerialName("tokenValidacion")
    val validationToken: String
)

/**
 * Extensión para convertir un TransactionRequest de dominio a DTO
 */
fun TransactionRequest.toDto(): TransactionRequestDto {
    return TransactionRequestDto(
        data = TransactionDataDto(
            originAccountCode = this.originAccountCode,
            destinationAccountCode = this.destinationAccountCode,
            transactionAmount = this.amount,
            description = this.description,
            currencyCode = this.currencyCode,
            transactionType = this.transactionType,
            validationToken = this.validationToken
        )
    )
}
