@file:Suppress("unused")

package com.example.data.dto.account

import android.annotation.SuppressLint
import com.example.domain.model.account.Account
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class AccountDto(
    @SerialName("codigoCuenta") val accountCode: String? = null,
    @SerialName("codigoCuentaMascara") val maskedAccountCode: String? = null,
    @SerialName("descripcionProducto") val productDescription: String? = null,
    @SerialName("simboloMoneda") val currencySymbol: String? = null,
    @SerialName("saldoDisponible") val availableBalance: Double? = null,
    @SerialName("codigoMoneda") val currencyCode: String? = null
)

/**
 * Mapper: DTO -> Domain
 */
fun AccountDto.toDomain(): Account {
    return Account(
        accountCode = accountCode.orEmpty(),
        maskedAccountCode = maskedAccountCode.orEmpty(),
        productDescription = productDescription.orEmpty(),
        currencySymbol = currencySymbol.orEmpty(),
        availableBalance = availableBalance ?: 0.0,
        currencyCode = currencyCode.orEmpty()
    )
}
