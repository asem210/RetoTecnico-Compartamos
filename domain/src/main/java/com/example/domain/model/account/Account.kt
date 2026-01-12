package com.example.domain.model.account

import kotlinx.serialization.Serializable

/**
 * Modelo de dominio para una cuenta bancaria
 */
@Serializable
data class Account(
    val accountCode: String,
    val maskedAccountCode: String,
    val productDescription: String,
    val currencySymbol: String,
    val availableBalance: Double,
    val currencyCode: String
)
