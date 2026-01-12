package com.example.data.remote.account

import com.example.data.dto.BaseResponse
import com.example.data.dto.account.AccountDto
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * API Service para operaciones de cuentas
 */
interface AccountApiService {
    
    /**
     * Obtiene las cuentas por ID de usuario
     * @param id ID del usuario
     * @return Lista de cuentas del usuario
     */
    @GET("api/account/{id}")
    suspend fun getAccountsById(@Path("id") id: String): BaseResponse<List<AccountDto>>
}
