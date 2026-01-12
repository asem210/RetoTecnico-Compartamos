package com.example.data.remote.transaction

import com.example.data.dto.BaseResponse
import com.example.data.dto.transaction.TransactionRequestDto
import com.example.data.dto.transaction.TransactionResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interfaz de servicio API para operaciones relacionadas con transacciones.
 */
interface TransactionApiService {
    @POST("api/transaction/create")
    suspend fun createTransaction(@Body request: TransactionRequestDto): BaseResponse<TransactionResponseDto>
}
