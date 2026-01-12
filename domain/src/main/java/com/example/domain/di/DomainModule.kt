package com.example.domain.di

import com.example.domain.usecase.account.GetAccountByIdUseCase
import org.koin.dsl.module

/**
 * Módulo de inyección de dependencias para la capa de dominio
 * Aquí se registran los casos de uso
 */
val domainModule = module {
    // Account use cases
    factory { GetAccountByIdUseCase(get()) }
    
    // Transaction use cases
    factory { com.example.domain.usecase.transaction.CreateTransactionUseCase(get()) }
}
