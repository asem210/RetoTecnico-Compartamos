package com.example.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Respuesta base para todas las APIs
 * @param T Tipo de dato en la respuesta
 */
@Serializable
data class BaseResponse<T>(
    @SerialName("data") val data: T? = null,
    @SerialName("success") val success: Boolean? = null,
    @SerialName("codeMessage") val codeMessage: String? = null,
    @SerialName("message") val message: String? = null,
    @SerialName("typeMessage") val typeMessage: Int? = null
)
