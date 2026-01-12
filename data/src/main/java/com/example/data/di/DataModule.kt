package com.example.data.di

import com.example.data.datasource.account.AccountDataSource
import com.example.data.remote.RetrofitProvider
import com.example.data.remote.account.AccountApiService
import com.example.data.repository.account.AccountRepositoryImpl
import com.example.domain.repository.account.AccountRepository
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Módulo de inyección de dependencias para la capa de datos
 * Aquí se registran repositories, data sources y API services
 */
val dataModule = module {
    // Retrofit - Singleton
    single { RetrofitProvider.provideRetrofit() }
    
    // API Services - Account
    factory<AccountApiService> {
        get<Retrofit>().create(AccountApiService::class.java)
    }
    
    // API Services - Transaction
    factory<com.example.data.remote.transaction.TransactionApiService> {
        get<Retrofit>().create(com.example.data.remote.transaction.TransactionApiService::class.java)
    }
    
    // Data Sources - Account
    factory { AccountDataSource(get()) }
    
    // Data Sources - Transaction
    factory { com.example.data.datasource.transaction.TransactionDataSource(get()) }
    
    // Repositories - Account
    factory<AccountRepository> { AccountRepositoryImpl(get()) }
    
    // Repositories - Transaction
    factory<com.example.domain.repository.transaction.TransactionRepository> {
        com.example.data.repository.transaction.TransactionRepositoryImpl(get())
    }
}
