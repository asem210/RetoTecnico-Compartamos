package com.example.data.datasource.account

import com.example.data.dto.BaseResponse
import com.example.data.dto.account.AccountDto
import com.example.data.remote.account.AccountApiService
import kotlin.random.Random

/**
 * Data Source para operaciones de cuentas
 * Incluye datos mockeados con valores aleatorios
 */
class AccountDataSource(
    private val apiService: AccountApiService
) {
    /**
     * Obtiene las cuentas por ID de usuario
     * @param id ID del usuario
     * @return Respuesta con lista de cuentas (mockeadas si falla la API)
     */
    suspend fun getAccountsById(id: String): BaseResponse<List<AccountDto>> {
        return try {
            apiService.getAccountsById(id)
        } catch (e: Exception) {
            // Retornar datos mockeados con valores aleatorios
            BaseResponse(
                data = getMockedAccounts(),
                success = true,
                message = "Datos de prueba (API no disponible)",
                codeMessage = "MOCKED_DATA",
                typeMessage = 0
            )
        }
    }
    
    /**
     * Genera un número de cuenta enmascarado aleatorio
     */
    private fun generateMaskedAccount(): String {
        val lastFourDigits = Random.nextInt(1000, 9999)
        return "**** **** **** $lastFourDigits"
    }
    
    /**
     * Genera un balance aleatorio entre 100 y 10,000
     */
    private fun generateRandomBalance(): Double {
        return Random.nextDouble(100.0, 10000.0)
    }
    
    /**
     * Datos mockeados con valores aleatorios
     * Todas las cuentas en PEN (Soles)
     */
    private fun getMockedAccounts(): List<AccountDto> {
        return listOf(
            AccountDto(
                accountCode = "ACC${Random.nextInt(100, 999)}",
                maskedAccountCode = generateMaskedAccount(),
                productDescription = "Cuenta de Ahorros",
                currencySymbol = "S/",
                availableBalance = generateRandomBalance(),
                currencyCode = "PEN"
            ),
            AccountDto(
                accountCode = "ACC${Random.nextInt(100, 999)}",
                maskedAccountCode = generateMaskedAccount(),
                productDescription = "Cuenta Corriente",
                currencySymbol = "S/",
                availableBalance = generateRandomBalance(),
                currencyCode = "PEN"
            ),
            AccountDto(
                accountCode = "ACC${Random.nextInt(100, 999)}",
                maskedAccountCode = generateMaskedAccount(),
                productDescription = "Cuenta Premium",
                currencySymbol = "S/",
                availableBalance = generateRandomBalance(),
                currencyCode = "PEN"
            )
        )
    }
}
